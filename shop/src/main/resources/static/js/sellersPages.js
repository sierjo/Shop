function filterOrders() {
    // То что ввел пользователь переводится в верхний регистр
    let input = document.getElementById("searchInput");
    let filter = input.value.toUpperCase();

    // Таблица со строками
    let table = document.getElementById("ordersTable");
    let tr = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

    // Прохо по строкам
    for (let i = 0; i < tr.length; i++) {
        // Пропуск троки "Brak gotowych zamówień"
        if (tr[i].classList.contains("empty-message")) continue;

        // Строка с  "Full Name"
        let tdName = tr[i].getElementsByTagName("td")[1];

        if (tdName) {
            let txtValue = tdName.textContent || tdName.innerText;
            // Если были совпадения то отображение строки
            if (txtValue.indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}