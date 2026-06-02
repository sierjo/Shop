// Токен
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
document.addEventListener("DOMContentLoaded", function () {
    fetch('/products/favoriteProduct/exist', {
        method: 'GET',
        headers: {
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Распаковываем ответ сервера в JSON
            } else {
                throw new Error('Ошибка сервера');
            }
        })
        .then(favoriteIds => {
            // массив чисел []
            console.log("ID избранных товаров:", favoriteIds);

            // Теперь можно закрасить нужные сердечки
            favoriteIds.forEach(id => {
                let heartIcon = document.getElementById('fevor-add' + id);
                if (heartIcon) {
                    heartIcon.outerHTML = `
                    <svg id="fevor-added${id}" width="24" height="24" viewBox="0 0 32.00 32.00"
                        xmlns="http://www.w3.org/2000/svg">
                            <defs/>
                            <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
                                  id="tSvgbe7afa32cf" title="Path 11"
                                  d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
                        </svg>`;
                }
            });
        }).catch(error => console.error('Ошибка:', error));
    // Отправка в метод контроллера `updateProductParameters`
    fetch('/strona/favorites/favoriteProduct', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken// Передача токена на сервер!
        },
        body: params // Передача коллекции данных на контроллер
    })
        .then(response => {
            if (response.ok) {
                // alert("Товар успешно добавлен в избранное!");
                let oldIcon = document.getElementById('fevor-add' + productId)
                oldIcon.outerHTML = `
                <svg id="fevor-added${productId}"  width="24" height="24" viewBox="0 0 32.00 32.00"
                    xmlns="http://www.w3.org/2000/svg">
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
                    oldIcon.outerHTML = `
                        <svg id="fevor-added${productId}" width="24" height="24" viewBox="0 0 32.00 32.00"
                            xmlns="http://www.w3.org/2000/svg">
                            <defs/>
                            <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
                                  id="tSvgbe7afa32cf" title="Path 11"
                                  d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
                        </svg>`;
                else if (newIcon)
                    newIcon.outerHTML = `
                    <svg id="fevor-add${productId}" class="my-custom-icon" width="24" height="24" viewBox="0 0 32.00 32.00" fill="#492f2f"
                        xmlns="http://www.w3.org/2000/svg">
                        <defs/>
                        <path 
                                    d="M18.14 2.5C20.8827 2.5655 23.5559 5.337 23.5 8.08C23.5 14.28 15.73 20.66 13 22.5C10.27 20.65 2.5 14.27 2.5 8.08C2.4441 5.337 5.1173 2.5655 7.86 2.5C9.2574 2.509 10.6006 2.9304 11.5 4C12.0067 4.6 12.4933 5.4 13 6C13.5067 5.4033 13.9933 4.5967 14.5 4C15.3975 2.9267 16.7409 2.5115 18.14 2.5ZM18.14 1C16.1573 1.0063 14.2777 1.8839 13 3.4C11.7223 1.8839 9.8427 1.0063 7.86 1C4.0104 1.0603 0.9388 4.2304 1 8.08C1 16.92 13 24 13 24C13 24 25 16.92 25 8.08C25.0612 4.2304 21.9896 1.0603 18.14 1Z"
                                    />
                    </svg>`;
            } else {
                // Любая другая непредвиденная ошибка (403, 500 и т.д.)
                alert("Something wrong: " + response.status);
            }
        })
        .catch(error => console.error('Ошибка:', error));
});
// function addTOFavorite(productId) {
//
//     // Создание объекта с параметрами
//     let params = new URLSearchParams();
//
//     params.append('productId', productId);
//     // params.append('deletes', deletProduct);
//
//     // Отправка в метод контроллера `updateProductParameters`
//     fetch('/strona/favorites/favoriteProduct', {
//         method: 'POST',
//
//         body: params // Передача коллекции данных на контроллер
//     })
//         .then(response => {
//             if (response.ok) {
//                 // alert("Товар успешно добавлен в избранное!");
//                 let oldIcon = document.getElementById('fevor-add' + productId)
//                 if (oldIcon){
//                     let parentElement = oldIcon.closest('.products-catalog');
//                     parentElement.remove();
//                 }
//                 oldIcon.outerHTML = `
//                 <svg id="fevor-added${productId}" xmlns="http://www.w3.org/2000/svg" width="17" height="12" viewBox="0 0 32.00 32.00"
//                      data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
//                     <defs/>
//                     <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
//                           id="tSvgbe7afa32cf" title="Path 11"
//                           d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
//                 </svg>`;
//             } else if (response.status === 409) {
//
//                 // По id продукта берём
//                 let container = document.getElementById('error-text' + productId);
//
//
//                 let oldIcon = document.getElementById('fevor-add' + productId)
//                 let newIcon = document.getElementById('fevor-added' + productId)
//                 if (oldIcon)
//                     oldIcon.outerHTML = `
//                         <svg id="fevor-added${productId}" xmlns="http://www.w3.org/2000/svg" width="17" height="12" viewBox="0 0 32.00 32.00"
//                              data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
//                             <defs/>
//                             <path fill="#ee0b0b" stroke="none" fill-opacity="1" stroke-width="1" stroke-opacity="1"
//                                   id="tSvgbe7afa32cf" title="Path 11"
//                                   d="M21.1402 7.4C23.883 7.4655 26.0561 9.337 26.0002 12.08C26.0002 17.6658 21.2868 21.6106 18.0253 24.1499C17.6559 24.4375 16.5949 25.2053 16 25C15.3522 24.7765 15.1734 23.5621 15.0242 23.0548C14.2025 20.2619 13.7186 17.1581 15.5002 13C17.5846 8.1352 19.7412 7.4115 21.1402 7.4ZM21.1402 5C19.1576 5.0063 17.278 5.8839 16.0002 7.4C14.7225 5.8839 12.8429 5.0063 10.8602 5C7.0106 5.0603 3.939 8.2304 4.0002 12.08C4.0002 20.92 16.0002 28 16.0002 28C16.0002 28 28.0002 20.92 28.0002 12.08C28.0615 8.2304 23.0376 5.0297 21.1402 5Z"/>
//                         </svg>`;
//                 else if (newIcon)
//                     newIcon.outerHTML = `
//                     <svg id="fevor-add${productId}" class="my-custom-icon" xmlns="http://www.w3.org/2000/svg"
//                                      width="17" height="12"
//                                      viewBox="0 0 26.00 25.00" fill="#492f2f"
//                                      data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
//                                     <defs/>
//                                     <path d="M18.14 2.5C20.8827 2.5655 23.5559 5.337 23.5 8.08C23.5 14.28 15.73 20.66 13 22.5C10.27 20.65 2.5 14.27 2.5 8.08C2.4441 5.337 5.1173 2.5655 7.86 2.5C9.2574 2.509 10.6006 2.9304 11.5 4C12.0067 4.6 12.4933 5.4 13 6C13.5067 5.4033 13.9933 4.5967 14.5 4C15.3975 2.9267 16.7409 2.5115 18.14 2.5ZM18.14 1C16.1573 1.0063 14.2777 1.8839 13 3.4C11.7223 1.8839 9.8427 1.0063 7.86 1C4.0104 1.0603 0.9388 4.2304 1 8.08C1 16.92 13 24 13 24C13 24 25 16.92 25 8.08C25.0612 4.2304 21.9896 1.0603 18.14 1Z"/>
//                                 </svg>`;
//             } else {
//                 // Любая другая непредвиденная ошибка (403, 500 и т.д.)
//                 alert("Something wrong: " + response.status);
//             }
//         })
//         //     .then(deletes => {
//         //     // Просмотр элементов в избранном
//         //     console.log("ID избранных товаров:", deletes);
//         //
//         //     // Теперь можно закрасить нужные сердечки
//         //     if (deletes) {
//         //         let heartIcon = document.getElementById('fevor-added' + id);
//         //         if (heartIcon) {
//         //             heartIcon.outerHTML = `
//         //             <svg th:id="fevor-add${productId}" class="my-custom-icon" xmlns="http://www.w3.org/2000/svg"
//         //                  width="17" height="12"
//         //                  viewBox="0 0 26.00 25.00" fill="#492f2f"
//         //                  data-guides="{&quot;vertical&quot;:[],&quot;horizontal&quot;:[]}">
//         //                 <defs/>
//         //                 <path
//         //                     d="M18.14 2.5C20.8827 2.5655 23.5559 5.337 23.5 8.08C23.5 14.28 15.73 20.66 13 22.5C10.27 20.65 2.5 14.27 2.5 8.08C2.4441 5.337 5.1173 2.5655 7.86 2.5C9.2574 2.509 10.6006 2.9304 11.5 4C12.0067 4.6 12.4933 5.4 13 6C13.5067 5.4033 13.9933 4.5967 14.5 4C15.3975 2.9267 16.7409 2.5115 18.14 2.5ZM18.14 1C16.1573 1.0063 14.2777 1.8839 13 3.4C11.7223 1.8839 9.8427 1.0063 7.86 1C4.0104 1.0603 0.9388 4.2304 1 8.08C1 16.92 13 24 13 24C13 24 25 16.92 25 8.08C25.0612 4.2304 21.9896 1.0603 18.14 1Z"/>
//         //             </svg>`;
//         //         }
//         //     }
//         // })
//         .catch(error => console.error('Ошибка:', error));
// }
function deleteFavoriteProduct(productId) {
    // 1. (Опционально) Спрашиваем пользователя, точно ли он хочет удалить
    if (!confirm("Удалить этот товар?")) {
        return; // Если нажал "Отмена", прерываем функцию
    }

    // 2. Отправляем запрос на ваш контроллер
    // Замените URL на тот, который удаляет товар в вашем Spring контроллере
    fetch('/strona/favorites/delete?productId=' + productId, {
        method: 'POST', // Или 'DELETE', если ваш контроллер настроен на @DeleteMapping
        headers: {
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