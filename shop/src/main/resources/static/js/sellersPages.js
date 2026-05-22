function startSearch() {
    let inputField = document.getElementById("searchInput");
    let findValue = inputField.value.trim();
    let tableBody = document.getElementById("ordersTableBody");

    // Если поле пустое просим ввести текст
    if (findValue === "") {
        tableBody.innerHTML = '<tr><td colspan="6" class="empty-message">Proszę wpisać tekst do wyszukiwania.</td></tr>';
        return;
    }


    // Индикатор загрузки ордеров
    tableBody.innerHTML = '<tr><td colspan="6" class="empty-message">Search →</td></tr>';

    // Формируем тело запроса
    let formData = new URLSearchParams();
    formData.append('findValue', findValue);

    fetch('/seller/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Server Error');
            }
            return response.json(); // JSON
        })
        .then(orders => {
            tableBody.innerHTML = ''; // Очистка старых результатов

            // Уведомление об отсутствии совпадений
            if (orders.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="6" class="empty-message">Nothing was found for: ' + findValue + '</td></tr>';
                return;
            }

            // Таблица для элементв
            orders.forEach(order => {
                let tr = document.createElement('tr');
                tr.id = `order-row${order.orderId}`
                tr.innerHTML = `
                <td>${order.orderId}</td>
                <td>${order.fullName}</td>
                <td>${order.additionDate}</td>
                <td>${order.city}</td>
                <td>${order.sumCostOrder} PLN</td>
                <td>
                    <button class="btn-details" onclick="informationDetails(${order.orderId})">Szczegóły</button> 
                </td>
            `;
                tableBody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Błąd:', error);
            tableBody.innerHTML = '<tr><td colspan="6" class="empty-message" style="color:red;">Error during user order search</td></tr>';
        });
}

// Поиск по нажатию клавиши Enter
document.getElementById("searchInput").addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        startSearch();
    }
});

let currentOpenOrderId = null;

function informationDetails(orderId) {
    currentOpenOrderId = orderId; // ID заказа

    // Шапка окна
    document.getElementById('modalOrderId').innerText = 'Order №: ' + orderId;

    let tableBody = document.getElementById('modalTableBody');
    tableBody.innerHTML = '<tr><td colspan="3">Loading...</td></tr>';

    let formData = new URLSearchParams();
    formData.append('itemId', orderId);

    fetch('/seller/order/details', {
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
            return response.json();
        })
        .then(realItems => {
            tableBody.innerHTML = '';

            realItems.forEach(item => {
                let tr = document.createElement('tr');
                let itemPrice = item.itemPrice;
                tr.innerHTML = `
                <td style="text-align: left;">${item.productName}</td>
                <td>${item.quantity}</td>
                <td>${itemPrice} PLN</td>
            `;
                tableBody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
            tableBody.innerHTML = '<tr><td colspan="3" style="color:red;">Products loading error</td></tr>';
        });

    let modal = document.getElementById('orderModal');
    modal.style.display = 'block';
}

function closeModal() {
    document.getElementById('orderModal').style.display = 'none';
    currentOpenOrderId = null;
}

// Кнопки Возврата и Выдачи
function giveOrder() {
    let formData = new URLSearchParams();
    formData.append('orderId', currentOpenOrderId);

    fetch('/seller/clientPicksUp', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Server Error');
            } else {
                let row = document.getElementById(`order-row${currentOpenOrderId}`) // Id Строки с ордером
                row.remove(); // Удаление этой строки со строницы
                closeModal();
            }
        })
}

function returnOrder() {
    let formData = new URLSearchParams();
    formData.append('orderId', currentOpenOrderId);

    fetch('/seller/clientRefund', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Server Error');
            } else {
                let row = document.getElementById(`order-row${currentOpenOrderId}`) // Id Строки с ордером
                row.remove(); // Удаление этой строки со строницы
                closeModal();
            }
        })
}


// Перетаскивание окна
dragElement(document.getElementById("orderModal"));

function dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    var header = document.getElementById("modalHeader");

    // Перетаскивание за верх
    if (header) {
        header.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;

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