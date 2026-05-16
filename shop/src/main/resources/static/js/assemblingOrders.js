function takeOrder(buttonElement, orderId) {
    // Определение по какой карточке кликнули
    let orderCard = document.getElementById('order-' + orderId);

    // Получение Id левого меню
    let sidebarList = document.getElementById('active-orders-list');

    // Перемещение карточки в левое менюи и удаление с центра
    sidebarList.appendChild(orderCard);
}


// Отслеживание кликов по левой панели
document.getElementById('active-orders-list').addEventListener('click', function (event) {
    // элемент по которому кликнули
    let orderCard = event.target.closest('.order-card');

    // Если клик действительно был по карточке (а не по пустому фону панели)
    if (orderCard) {
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
            'Content-Type': 'application/x-www-form-urlencoded',
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