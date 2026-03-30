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
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
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

function addTOFavorite(productId) {

    // Создание объекта с параметрами
    let params = new URLSearchParams();

    params.append('productId', productId);


    // Отправка в метод контроллера `updateProductParameters`
    fetch('/strona/favoriteProduct', {
        method: 'POST',

        body: params // Передача коллекции данных на контроллер
    })
        .then(response => {
            if (response.ok) {
                alert("Товар успешно добавлен в избранное!");
                let oldIcon = document.getElementById('fevor-add' + productId)
                oldIcon.outerHTML = `
                <svg id="fevor-added${productId}" xmlns="http://www.w3.org/2000/svg" width="17" height="12" viewBox="0 0 32.00 32.00"
                     data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
                    <defs/>
                    <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
                          id="tSvgbe7afa32cf" title="Path 11"
                          d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
                </svg>`;
            } else if (response.status === 409) {

                // По id продукта берём
                let container = document.getElementById('error-text' + productId);


                let oldIcon = document.getElementById('fevor-add' + productId)
                let newIcon = document.getElementById('fevor-added' + productId)
                if (oldIcon)
                    oldIcon.outerHTML =`
                        <svg id="fevor-added${productId}" xmlns="http://www.w3.org/2000/svg" width="17" height="12" viewBox="0 0 32.00 32.00"
                             data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
                            <defs/>
                            <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
                                  id="tSvgbe7afa32cf" title="Path 11"
                                  d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
                        </svg>`;
                else if (newIcon)
                newIcon.outerHTML =`
                    <svg id="fevor-added${productId}" xmlns="http://www.w3.org/2000/svg" width="17" height="12" viewBox="0 0 32.00 32.00"
                             data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
                            <defs/>
                            <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
                                  id="tSvgbe7afa32cf" title="Path 11"
                                  d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
                        </svg>`;
                container.textContent = 'Już jest ♥';
                console.log("editProduct 119")
                console.log(oldIcon)
                // if (oldIcon == null)
                //     oldIcon = document.getElementById('fevor-added' + productId)
                //     console.log(oldIcon)
                    // if (oldIcon == true)
                    //     container.textContent = 'Już jest ♥';

                setTimeout(() => {
                    // Через 3 секунды убирает надпись
                    // After 3 seconds the label will gone.
                    container.textContent = '';

                }, 3000);
            } else {
                // Любая другая непредвиденная ошибка (403, 500 и т.д.)
                alert("Something wrong: " + response.status);
            }
        })
        .catch(error => console.error('Ошибка:', error));
}