// Токен
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function takeOrder(buttonElement, orderId) {
    let formData = new URLSearchParams();
    formData.append('itemId', orderId);
    fetch('/order/take', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
        body: formData
    })
        .then(response => {
                if (response.ok) {
                    // Определение по какой карточке кликнули
                    let orderCard = document.getElementById('order-' + orderId);

                    // Получение Id левого меню
                    let sidebarList = document.getElementById('active-orders-list');

                    // Крестик для левой карточки
                    let closeBtn = document.createElement('span');
                    closeBtn.className = 'leftPanel-close-order'; // Применяем наш новый класс
                    closeBtn.innerText = '✖';
                    closeBtn.onclick = function (e) {
                        closeLeftPanelOrder(e, this, orderId);
                    };

                    // Вставка созданного крестика
                    orderCard.appendChild(closeBtn);
                    // Перемещение карточки в левое менюи и удаление с центра
                    sidebarList.appendChild(orderCard);

                } else {
                    alert("Error: Couldn't take the order to work.");
                }
            }
        )
        .catch(error => {
            console.error("Network error:", error);
        });
}

function closeLeftPanelOrder(event, buttonElement, orderId) {

    /* Из-за того что крестик нахлдится в области которая сама по себе является триггером сабытия по нажатию
    * что-бы небыло каскадного вызова событий исспользуется этот эвет */
    event.stopPropagation();


    let formData = new URLSearchParams();
    formData.append('itemId', orderId);

    // Отправляем запрос на сервер
    fetch('/order/close', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken // Не забываем про токен безопасности!
        },
        body: formData
    })
        .then(response => {
            if (response.ok) {
                // Карточка крестик которой был нажат
                let orderCard = document.getElementById('order-' + orderId);

                let availableList = document.getElementById('available-orders-list');

                // карточка перемещается обратно
                availableList.appendChild(orderCard);

                // Крестик скрыывается
                buttonElement.style.display = 'none';

                // Добавление кнопки "Tace Order"
                let takeBtn = orderCard.querySelector('.btn-take');
                if (takeBtn) {
                    takeBtn.style.display = 'inline-block';
                } else {
                    takeBtn = document.createElement('button');
                    takeBtn.className = 'btn-take';
                    takeBtn.innerText = 'Do pracy';
                    takeBtn.onclick = function () {
                        takeOrder(this, orderId);
                    };
                    orderCard.appendChild(takeBtn);
                }

            } else {
                alert("Ошибка при закрытии заказа. Попробуйте обновить страницу.");
            }
        })
        .catch(error => {
            console.error("Ошибка сети:", error);
        });
}

// Отслеживание кликов по левой панели
document.getElementById('active-orders-list').addEventListener('click', function (event) {
    // элемент по которому кликнули
    let orderCard = event.target.closest('.order-card');

    // Если клик действительно был по карточке (а не по пустому фону панели)
    if (orderCard && !event.target.classList.contains('leftPanel-close-order')) {
        // ID заказа
        let orderId = orderCard.id.replace('order-', '');
        // Город для заголовка окна
        let cityText = orderCard.querySelector('.order-info p:first-child span').innerText;

        // Открытие модального окна
        openModal(orderId, cityText);
    }
});


// Модвльное Окно
let currentOpenOrderId = null;

function openModal(orderId, city) {
    currentOpenOrderId = orderId;

    // Шапка окна
    document.getElementById('modalCity').innerText = 'City: ' + city;
    document.getElementById('modalOrderId').innerText = 'Order №: ' + orderId;

    let tableBody = document.getElementById('modalTableBody');
    // Надпись что данные грузятся пока происходит поиск
    tableBody.innerHTML = '<tr><td colspan="3">Loading</td></tr>';

    let formData = new URLSearchParams();
    formData.append('itemId', orderId);
    fetch('/order/modalPanel', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети');
            }
            return response.json(); // Распаковываем ответ от сервера в формат JSON
        })
        .then(realItems => {// List<OrderItemModalDTO>
            tableBody.innerHTML = ''; // Скрытие надписи "о загрузке данных"

            // заполнение данными из List<OrderItemModalDTO>
            realItems.forEach(item => {
                let tr = document.createElement('tr');
                tr.innerHTML = `
                    <td style="text-align: left;">${item.productName}</td>
                    <td>${item.quantityRequired}</td>
                    <td>
                        <button class="counter-btn" onclick="changeCount(this, -1, ${item.quantityRequired})">-</button>
                        <span class="counter-value">0</span>
                        <button class="counter-btn" onclick="changeCount(this, 1, ${item.quantityRequired})">+</button>
                    </td>
                `;
                tableBody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
            tableBody.innerHTML = '<tr><td colspan="3" style="color:red;">Products loading error</td></tr>';
        });

    // Открываем окно
    let modal = document.getElementById('orderModal');
    modal.style.display = 'block';
}

function closeModal() {
    document.getElementById('orderModal').style.display = 'none';
    currentOpenOrderId = null;
}


// Счётчики збораВ
function changeCount(button, delta, maxRequired) {
    let span = button.parentElement.querySelector('.counter-value');
    let currentValue = parseInt(span.innerText);
    let newValue = currentValue + delta;

    // количество не может быть <0 и больше необходимого колличестваа
    if (newValue >= 0 && newValue <= maxRequired) {
        span.innerText = newValue;
    }
}


// Кнопка заказ собран
function completeOrder() {
    let allCollected = true;
    let rows = document.querySelectorAll('#modalTableBody tr');

    rows.forEach(row => {
        let required = parseInt(row.cells[1].innerText);
        let collected = parseInt(row.querySelector('.counter-value').innerText);
        if (required !== collected) {
            allCollected = false;
        }
    });

    if (!allCollected) {
        alert("Внимание: Вы собрали не все товары!");
        return;
    }
    // Отправка id собранного заказа
    let orderComplete = new URLSearchParams();
    orderComplete.append('orderId', currentOpenOrderId);
    fetch('/order/assembling/complete', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
        body: orderComplete
    })
        .then(response => {
            if (response.ok) {
                alert("Товар собран");
                document.getElementById('order-' + currentOpenOrderId).remove();
                closeModal();
            } else {
                throw new Error('Ошибка отпраки');
            }
        });
}


// Перетаскивание окна
dragElement(document.getElementById("orderModal"));

function dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    var header = document.getElementById("modalHeader");

    if (header) {
        header.onmousedown = dragMouseDown;
    } else {
        elmnt.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;
        // Оставляем возможность нажимать на крестик
        if (e.target.className === 'close-btn') return;

        e.preventDefault();
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
        elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
    }

    function closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
    }
}


// CHAT             ↓↓↓↓↓
let stompClient = null;
let currentSender = "";

function startChat(senderEmail) {
    currentSender = senderEmail;

    // Оодальное окно
    document.getElementById('chatModal').style.display = 'block';

    // Прогрузка истории если она есть
    loadChatHistory()

    // проверка на подключение
    if (stompClient === null) {
        // Подключение к эндпоинту в WebSocketConfig
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // Подключение к каналу
            stompClient.subscribe('/topic/public', function (messageOutput) {
                //данные с JSON
                let message = JSON.parse(messageOutput.body);
                showMessage(message.sender, message.content);
            });
        });
    }
}

function closeChat() {
    document.getElementById('chatModal').style.display = 'none';
}

function sendMessage() {
    let messageContent = document.getElementById('messageInput').value;

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: currentSender,
            content: messageContent
        };


        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));

        document.getElementById('messageInput').value = '';
    }
}


function showMessage(sender, content) {
    let chatArea = document.getElementById('chatArea');

    // строки сообщения
    let wrapper = document.createElement('div');
    wrapper.className = 'chat-message-wrapper';

    // отправитель
    let senderName = document.createElement('div');
    senderName.className = 'chat-sender-name';
    senderName.innerText = sender;

    // Само сообщение (пузырь)
    let messageBubble = document.createElement('div');
    messageBubble.innerText = content;

    // классы в зависимости от того кто отправил
    if (sender === currentSender) {
        messageBubble.className = 'chat-message me';
        // ответ на сообшение справа
        senderName.style.alignSelf = 'flex-end';
    } else {
        messageBubble.className = 'chat-message other';
    }

    wrapper.appendChild(senderName);
    wrapper.appendChild(messageBubble);
    chatArea.appendChild(wrapper);

    // прокрутка вниз
    chatArea.scrollTop = chatArea.scrollHeight;
}

// Перетаскивание Чата
dragChat(document.getElementById("chatModal"));

function dragChat(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    var header = elmnt.querySelector('.modal-header');

    if (header) {
        header.onmousedown = dragMouseDown;
    } else {
        elmnt.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;
        // Оставляем возможность нажимать на крестик
        if (e.target.className === 'close-btn') return;

        e.preventDefault();
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        document.onmousemove = ChatDrag;
    }

    function ChatDrag(e) {
        e = e || window.event;
        e.preventDefault();
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
        elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
    }

    function closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
    }
}

// История
function loadChatHistory() {
    fetch('/chat/history')
        .then(response => {
            if (!response.ok) throw new Error('History upload error');
            return response.json();
        })
        .then(messages => {
            let chatArea = document.getElementById('chatArea');
            chatArea.innerHTML = ''; // Очищаем окна

            // Проходи  по к сообщениям и восстановление
            messages.forEach(msg => {

                showMessage(msg.sender, msg.content);
            });
        })
        .catch(error => console.error("Error loading chat history:", error));
}
// CHAT             ↑↑↑↑↑