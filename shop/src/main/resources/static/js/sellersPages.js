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
                tr.innerHTML = `
                <td>${order.orderId}</td>
                <td>${order.fullName}</td>
                <td>${order.additionDate}</td>
                <td>${order.city}</td>
                <td>${order.sumCostOrder} PLN</td>
                <td>
                    <button class="btn-details" onclick="takeOrder(this, ${order.orderId})">Szczegóły</button>
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