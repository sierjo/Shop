// Токен
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function deleteCartProduct(productId) {
    // 1. (Опционально) Спрашиваем пользователя, точно ли он хочет удалить
    if (!confirm(`Удалить этот товар ${productId} из корзины?`)) {
        return; // Если нажал "Отмена", прерываем функцию
    }

    // 2. Отправляем запрос на ваш контроллер
    // Замените URL на тот, который удаляет товар в вашем Spring контроллере
    fetch('/cartProduct/delete?productId=' + productId, {
        method: 'POST', // Или 'DELETE', если ваш контроллер настроен на @DeleteMapping
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
    })
        .then(response => {
            if (response.ok) {
                // 3. Сервер успешно удалил данные. Теперь удаляем HTML.
                // Ищем карточку по её уникальному ID
                let cardElement = document.getElementById('product-card-' + productId);

                if (cardElement) {
                    cardElement.remove(); // Удаляем элемент со страницы
                }
            } else {
                alert("Ошибка при удалении. Статус сервера: " + response.status);
            }
        })
        .catch(error => {
            console.error('Ошибка сети:', error);
            alert("Произошла ошибка при соединении с сервером.");
        });
}

function updateQuantity(productId, quantity, productPrice) {
    let newQuantity = Number(quantity);
    // let newQuantity = quantity;

    console.log(typeof newQuantity)

    // Проверка на 0 и отрицательное количество
    if (isNaN(newQuantity) || newQuantity === 0) {
        newQuantity = 1;
    } else if (newQuantity < 0) {
        newQuantity = Math.abs(newQuantity);
    }

    // Отправка даннх на сервер для сохранения в БД
    let formData = new URLSearchParams();
    formData.append('itemId', productId);
    formData.append('quantity', newQuantity);
    fetch('/cart/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                alert('Ошибка при сохранении количества в корзине!');
                throw new Error('запрещённое число');
            }
            return response.text(); // Читаем новую сумму от Java-контроллера!
        })
        .then(newTotalSum => {
            // Обновление итоговой суммы
            let grandTotalElement = document.getElementById('total-price');
            let fixedQuantity = document.getElementById('quantity_input' + productId);
            if (grandTotalElement) {
                grandTotalElement.innerText = newTotalSum + ' PLN';
            }
            if (fixedQuantity) {
                fixedQuantity.value = newQuantity;
            }
        })
        .catch(error => console.error('Ошибка:', error));
}

