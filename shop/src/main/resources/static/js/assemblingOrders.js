function takeOrder(buttonElement, orderId) {
    // Определение по какой карточке кликнули
    let orderCard = document.getElementById('order-' + orderId);

    // Получение Id левого меню
    let sidebarList = document.getElementById('active-orders-list');

    // Перемещаем карточку в левое менюи и удаление с центра
    sidebarList.appendChild(orderCard);
}