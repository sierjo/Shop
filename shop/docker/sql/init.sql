CREATE DATABASE  IF NOT EXISTS `diplom_db_shop_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `diplom_db_shop_db`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: diplom_db_shop_db
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int NOT NULL,
                             `first_name` varchar(100) DEFAULT NULL,
                             `last_name` varchar(100) DEFAULT NULL,
                             `City` varchar(100) DEFAULT NULL,
                             `Country` varchar(100) DEFAULT NULL,
                             `Street` varchar(100) DEFAULT NULL,
                             `House_number` varchar(100) DEFAULT NULL,
                             `Apartment_number` varchar(100) DEFAULT NULL,
                             `Postal_code` varchar(20) NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `fk_address_user` (`user_id`),
                             CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (36,1,'KOLS','YTIF','Lublin','PL','Lawinowa','3','62','20-864'),(37,1,'NHS','PER','Lublin','PL','Lawinowa','3','629','20-864'),(38,1,'HENADZI','USHKEVICH','Lublin','PL','Lawinowa','3','62','20-864'),(39,1,'GHNR','UINV','Lublin','PL','Lawinowa','3','62','20-864'),(40,1,'SIARHEI','OLEG','Lublin','PL','Lawinowa','3','62','20-864'),(41,1,'NERT','UIT','Lublin','PL','Lawinowa','3','62','20-864'),(42,1,'SIARHEI','KOLI','Lublin78','PL','Lawinowa','888','777','20-86465'),(43,1,'OLEG','KOZLOW','Lublin','PL','Lawinowa','3','62','20-864'),(44,1,'OLTR','EPS','Lublin','PL','Lawinowa','3','62','20-864');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `cart_id` int NOT NULL,
                              `cart_product_id` int NOT NULL,
                              `quantity` int NOT NULL DEFAULT '0',
                              PRIMARY KEY (`id`),
                              KEY `fk_cart_items_cart` (`cart_id`),
                              KEY `fk_cart_items_product` (`cart_product_id`),
                              CONSTRAINT `fk_cart_items_cart` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE CASCADE,
                              CONSTRAINT `fk_cart_items_product` FOREIGN KEY (`cart_product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (72,1,1,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL,
                         `is_empty` bit(1) DEFAULT b'0',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `unique_user_cart` (`user_id`),
                         CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,1,_binary '\0'),(2,2,_binary '\0');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_products`
--

DROP TABLE IF EXISTS `favorite_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_products` (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `user_id` int NOT NULL,
                                     `product_id` int NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `unique_fav` (`user_id`,`product_id`),
                                     KEY `fk_fav_product` (`product_id`),
                                     CONSTRAINT `fk_fav_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE,
                                     CONSTRAINT `fk_fav_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_products`
--

LOCK TABLES `favorite_products` WRITE;
/*!40000 ALTER TABLE `favorite_products` DISABLE KEYS */;
INSERT INTO `favorite_products` VALUES (13,1,1),(12,1,2);
/*!40000 ALTER TABLE `favorite_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `order_id` int NOT NULL,
                               `product_id` int NOT NULL,
                               `quantity` int NOT NULL,
                               `price_at_purchase` decimal(10,2) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `fk_order_items_order` (`order_id`),
                               KEY `fk_order_items_product` (`product_id`),
                               CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
                               CONSTRAINT `fk_order_items_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (25,20,1,1,2500.00),(26,21,1,1,2500.00),(27,22,1,1,2500.00),(28,23,1,1,2500.00),(29,24,1,1,2500.00),(30,25,1,1,2500.00),(31,26,1,1,2500.00),(32,27,1,1,2500.00),(33,28,1,1,2500.00),(34,29,1,1,2500.00),(35,30,1,1,2500.00),(36,31,1,1,2500.00),(37,32,1,1,2500.00),(38,33,1,1,2500.00),(39,34,1,1,2500.00),(40,35,1,1,2500.00),(41,36,1,1,2500.00),(42,37,2,2,500.00),(43,38,1,1,2500.00),(44,39,1,8,2500.00),(45,39,2,6,500.00),(46,40,2,4,500.00),(47,40,1,1,2500.00),(48,41,1,1,2500.00),(49,41,2,1,500.00),(50,42,1,1,2500.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
                          `order_id` int NOT NULL AUTO_INCREMENT,
                          `user_id` int NOT NULL,
                          `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `status` varchar(50) NOT NULL,
                          `total_amount` decimal(10,2) NOT NULL,
                          `adress_id` int NOT NULL,
                          `shipping_city` varchar(100) NOT NULL DEFAULT 'Polska',
                          `shipping_street` varchar(255) NOT NULL,
                          `shipping_postal_code` varchar(20) NOT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `fk_order_user` (`user_id`),
                          KEY `fk_address_order` (`adress_id`),
                          CONSTRAINT `fk_address_order` FOREIGN KEY (`adress_id`) REFERENCES `addresses` (`id`) ON DELETE CASCADE,
                          CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (20,1,'2026-05-14 13:05:50','DONE',2500.00,36,'Lublin','Lawinowa 3/62','20-864'),(21,1,'2026-05-14 13:06:07','DONE',2500.00,36,'Lublin','Lawinowa 3/62','20-864'),(22,1,'2026-05-14 13:06:55','DONE',2500.00,37,'Lublin','Lawinowa 3/62','20-864'),(23,1,'2026-05-14 13:07:11','DONE',2500.00,37,'Lublin','Lawinowa 3/62','20-864'),(24,1,'2026-05-14 13:08:04','CREATED',2500.00,36,'Lublin','Lawinowa 3/62','20-864'),(25,1,'2026-05-14 13:11:41','CREATED',2500.00,36,'Lublin','Lawinowa 3/62','20-864'),(26,1,'2026-05-14 13:12:07','CREATED',2500.00,37,'Lublin','Lawinowatttttt 3/62','20-864'),(27,1,'2026-05-14 13:13:01','CREATED',2500.00,37,'Lublin','Lawinowa 3/620','20-864'),(28,1,'2026-05-14 13:32:54','CREATED',2500.00,36,'Lublin','Lawinowa 3/62','20-864'),(29,1,'2026-05-14 13:33:21','CREATED',2500.00,38,'Lublin','Lawinowa 3/62','20-864'),(30,1,'2026-05-14 13:34:02','CREATED',2500.00,38,'Lublin','Lawinowa 3/62','20-864'),(31,1,'2026-05-14 14:08:07','CREATED',2500.00,37,'Lublin','Lawinowa 3/62','20-864'),(32,1,'2026-05-14 14:13:33','CREATED',2500.00,37,'Lublin','Lawinowa 3/629','20-864'),(33,1,'2026-05-14 14:16:04','CREATED',2500.00,39,'Lublin','Lawinowa 3/62','20-864'),(34,1,'2026-05-14 14:22:59','CREATED',2500.00,37,'Lublin','Lawinowa 3/629','20-864'),(35,1,'2026-05-14 14:23:17','CREATED',2500.00,39,'Lublin','Lawinowa 3/62','20-864'),(36,1,'2026-05-14 16:20:41','CREATED',2500.00,40,'Lublin','Lawinowa 3/62','20-864'),(37,1,'2026-05-14 16:22:43','CREATED',1000.00,41,'Lublin','Lawinowa 3/62','20-864'),(38,1,'2026-05-15 11:59:30','DONE',2500.00,42,'Lublin78','Lawinowa 888/777','20-86465'),(39,1,'2026-05-15 13:27:24','DONE',23000.00,40,'Lublin','Lawinowa 3/62','20-864'),(40,1,'2026-05-16 11:25:50','DONE',4500.00,37,'Lublin','Lawinowa 3/629','20-864'),(41,1,'2026-05-18 12:44:04','CREATED',3000.00,43,'Lublin','Lawinowa 3/62','20-864'),(42,1,'2026-05-18 12:46:45','CREATED',2500.00,44,'Lublin','Lawinowa 3/62','20-864');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `product_id` int NOT NULL AUTO_INCREMENT,
                            `product_name` varchar(255) NOT NULL,
                            `description` text,
                            `product_price` decimal(10,2) NOT NULL,
                            `stock_quantity` int NOT NULL,
                            `product_photo` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Kit','Крутой кот который ест рыбу очень голодный кот',2500.00,324,'kit.png'),(2,'Kit2','Крутой кот который ест рыбу очень голодный кот',500.00,2,'kit.png');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `user_type_id` int NOT NULL AUTO_INCREMENT,
                         `user_type_name` varchar(50) NOT NULL,
                         PRIMARY KEY (`user_type_id`),
                         UNIQUE KEY `user_type_name` (`user_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Klient'),(3,'Magazynier'),(2,'Sprzedawca');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `email` varchar(100) DEFAULT NULL,
                         `password` varchar(255) NOT NULL,
                         `phone_number` varchar(20) DEFAULT NULL,
                         `is_active` bit(1) DEFAULT b'1',
                         `registration_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `role_id` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `fk_user_role` (`role_id`),
                         CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test@gmail.com','123','2341',_binary '','2026-05-06 13:06:29',1),(2,'test2@gmail.com','123','24',_binary '','2026-05-06 13:06:37',1),(3,'testm@gmail.com','123','34',_binary '','2026-05-06 13:06:48',3),(5,'testsz@gmail.com','123','8841',_binary '','2026-05-17 14:56:37',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'diplom_db_shop_db'
--

--
-- Dumping routines for database 'diplom_db_shop_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-28 14:22:20
