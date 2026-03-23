
function startEdit(id) {
    // Если нажата кнопка изменить текст который был будет убран
    document.getElementById('name-text-' + id).style.display = 'none';
    document.getElementById('price-text-' + id).style.display = 'none';
    document.getElementById('desc-text-' + id).style.display = 'none';
    document.getElementById('btn-edit-' + id).style.display = 'none';

    // Отображение полей ввода и кнопок сохранить и изменить
    document.getElementById('name-input-' + id).style.display = 'block';
    document.getElementById('price-input-' + id).style.display = 'block';
    document.getElementById('desc-input-' + id).style.display = 'block';
    document.getElementById('btn-save-' + id).style.display = 'inline-block';
    document.getElementById('btn-cancel-' + id).style.display = 'inline-block';
}

// Елсли нажата кнопка `отмена`
function cancelEdit(id) {
    // Выход из редактора
    document.getElementById('name-input-' + id).style.display = 'none';
    document.getElementById('price-input-' + id).style.display = 'none';
    document.getElementById('desc-input-' + id).style.display = 'none';
    document.getElementById('btn-save-' + id).style.display = 'none';
    document.getElementById('btn-cancel-' + id).style.display = 'none';

    // Отображение скрытого текста
    document.getElementById('name-text-' + id).style.display = 'block';
    document.getElementById('price-text-' + id).style.display = 'block';
    document.getElementById('desc-text-' + id).style.display = 'block';
    document.getElementById('btn-edit-' + id).style.display = 'inline-block';
}

// СОХРАНЕНИЕ: Елсли нажата кнопка `сохранить`
function saveEdit(id) {
    // Считывание новых значений
    let newName = document.getElementById('name-input-' + id).value;
    let newPrice = document.getElementById('price-input-' + id).value;
    let newDesc = document.getElementById('desc-input-' + id).value;

    // Создание объекта с параметрами
    let params = new URLSearchParams();
    params.append('id', id);
    params.append('name', newName);
    params.append('price', newPrice);
    params.append('description', newDesc);

    // Отправка в метод контроллера `updateProductParameters`
    fetch('/products/update-product', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params // Передача коллекции данных на контроллер
    })
        .then(response => {
            if (response.ok) {
                // Обновление текста на экране
                document.getElementById('name-text-' + id).innerText = newName;
                document.getElementById('price-text-' + id).innerText = newPrice + ' PLN';
                document.getElementById('desc-text-' + id).innerText = newDesc;

                cancelEdit(id); // Завершение режима редактирования
            } else {
                alert("Ошибка при сохранении!");
            }
        });
}