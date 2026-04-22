function deleteCartProduct(productId) {
    // 1. (Опционально) Спрашиваем пользователя, точно ли он хочет удалить
    if (!confirm(`Удалить этот товар ${productId} из корзины?`)) {
        return; // Если нажал "Отмена", прерываем функцию
    }

    // 2. Отправляем запрос на ваш контроллер
    // Замените URL на тот, который удаляет товар в вашем Spring контроллере
    fetch('/cartProduct/delete?productId=' + productId, {
        method: 'POST' // Или 'DELETE', если ваш контроллер настроен на @DeleteMapping
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

