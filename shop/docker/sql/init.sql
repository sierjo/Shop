
DROP DATABASE IF EXISTS diplom_db_shop_db;
CREATE DATABASE diplom_db_shop_db;
USE diplom_db_shop_db;


-- РОЛИ ПОЛЬЗОВАТЕЛЕЙ

CREATE TABLE `roles` (
                         `user_type_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `user_type_name` VARCHAR(50) NOT NULL UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci; -- Движок хранения таблицы

INSERT INTO `roles` VALUES (1,'Klient'),(2,'Sprzedawca'),(3,'Magazynier');


-- ПОЛЬЗОВАТЕЛИ

CREATE TABLE `users` (
                         `id` int AUTO_INCREMENT PRIMARY KEY,
                         `email` VARCHAR(100) DEFAULT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `phone_number` VARCHAR(20),
                         `is_active` BIT(1) DEFAULT 1,
                         `registration_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         `role_id` int DEFAULT NULL,
                         CONSTRAINT fk_user_role FOREIGN KEY (`role_id`) REFERENCES `roles`(`user_type_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- АДРЕСА

CREATE TABLE `addresses` (
                             `id` int AUTO_INCREMENT PRIMARY KEY,
                             `user_id` int NOT NULL,
                             `first_name` VARCHAR(100),
                             `last_name` VARCHAR(100),
                             `City` VARCHAR(100),
                             `Street` VARCHAR(100),
                             `House_number` VARCHAR(100),
                             `Apartment_number` VARCHAR(100),
                             `Postal_code` VARCHAR(20) NOT NULL,
                             CONSTRAINT fk_address_user FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ТОВАРЫ

CREATE TABLE `products` (
                            `product_id` int AUTO_INCREMENT PRIMARY KEY,
                            `product_name` VARCHAR(255) NOT NULL,
                            `description` TEXT,
                            `product_price` DECIMAL(10, 2) NOT NULL,
                            `stock_quantity` INT NOT NULL,
                            `product_photo` VARCHAR(255) DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ИЗБРАННОЕ

CREATE TABLE `favorite_products` (
                                     `id` int AUTO_INCREMENT PRIMARY KEY,
                                     `user_id` int NOT NULL,
                                     `product_id` int NOT NULL,
                                     UNIQUE KEY unique_fav (`user_id`, `product_id`),
                                     CONSTRAINT fk_fav_user FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
                                     CONSTRAINT fk_fav_product FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- КОРЗИНА

-- Таблица корзины (у одного юзера одна корзина)
CREATE TABLE `carts` (
                         `id` int AUTO_INCREMENT PRIMARY KEY,
                         `user_id` int NOT NULL,
                         `is_empty` BIT(1) DEFAULT 0,
                         UNIQUE KEY unique_user_cart (`user_id`), -- У юзера только 1 корзина
                         CONSTRAINT fk_cart_user FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ТОВАРЫ В КОРЗИНЕ

CREATE TABLE `cart_items` (
                              `id` int AUTO_INCREMENT PRIMARY KEY,
                              `cart_id` int NOT NULL,
                              `cart_product_id` int NOT NULL,
                              `quantity` int NOT NULL DEFAULT 0, -- Изначальное количество товара в корзине
                              CONSTRAINT fk_cart_items_cart FOREIGN KEY (`cart_id`) REFERENCES `carts`(`id`) ON DELETE CASCADE,
                              CONSTRAINT fk_cart_items_product FOREIGN KEY (`cart_product_id`) REFERENCES `products`(`product_id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ЗАКАЗЫ

CREATE TABLE `orders` (
                          `order_id` int AUTO_INCREMENT PRIMARY KEY,
                          `user_id` int NOT NULL,
                          `order_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          `status` VARCHAR(50) NOT NULL,
                          `total_amount` DECIMAL(10, 2) NOT NULL,
                          `adress_id` int NOT NULL,

                          `shipping_city` VARCHAR(100) NOT NULL DEFAULT 'Polska',
                          `shipping_street` VARCHAR(255) NOT NULL,
                          `shipping_postal_code` VARCHAR(20) NOT NULL,

                          CONSTRAINT fk_order_user FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                          CONSTRAINT fk_address_order FOREIGN KEY (`adress_id`) REFERENCES `addresses`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ЭЛЕМЕНТЫ ЗАКАЗА

CREATE TABLE `order_items` (
                               `id` int AUTO_INCREMENT PRIMARY KEY,
                               `order_id` int NOT NULL,
                               `product_id` int NOT NULL,
                               `quantity` int NOT NULL,
                               `price_at_purchase` DECIMAL(10, 2) NOT NULL,
                               CONSTRAINT fk_order_items_order FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`) ON DELETE CASCADE,
                               CONSTRAINT fk_order_items_product FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;