# Shop
---

<p align="center">
<img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java" />
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security" />
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate" />
<img src="https://img.shields.io/badge/JPA-FF6600?style=for-the-badge&logo=jakartaee&logoColor=white" alt="JPA" />
<img src="https://img.shields.io/badge/WebSocket-000000?style=for-the-badge&logo=socket.io&logoColor=white" alt="WebSocket" />
<img src="https://img.shields.io/badge/REST-005571?style=for-the-badge&logo=databricks&logoColor=white" alt="REST API" />

<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis" />

<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf" />
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5" />
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3" />
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" alt="JavaScript" />
</p>

---

**Shop** This is a web store where customers can place an order by making a selection from the products on offer. The warehouse employee is in charge of assembling the order, and the pick-up point employee issues the received order to the customer.

---

## Table of Contents
* [Tech Stack](#-tech-stack)
* [How to Run the Project](#-how-to-run-the-project)
* [Project Structure](#-project-structure)
* [Database Structure](#-database-structure)
* [Screenshots](#-screenshots)

---

## Tech Stack

* **Java:** 21
* **Spring Boot:** 3.4.11
* **Spring Security, Hibernate, JPA**
* **Apache-Maven:** 4.0.0
* **Database:** MySQL, Redis (chat handling).
* **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript 
* **WebSocket**

---

## How to Run the Project

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/sierjo/Shop.git
    ```
2.  **Go to the project folder:**
    ```bash
    cd shop
    ```
3.  **Build the project:**
    ```bash
    mvn clean install
    ```
6.  **Run the Docker:**

  <p> You need have Docker app on you computer.</p>

  <p> Open terminal in the project's folder.</p>

    ```bash
    docker-compose up -d
    ```

  <p> Server will be available at `http://localhost:8081`.</p>
    

---

## 📂 Project Structure

```
shop/
│
├── docker/
│   └── sql/
│       └── init.sql
│
├── src/
│   ├── main/
│   │   ├── java/com/diplom_proj/shop/
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── AppConfig.java
│   │   │   │   ├── CustomAuthenticationSuccessHandler.java
│   │   │   │   ├── MvcConfig.java
│   │   │   │   ├── WebSecurityConfig.java
│   │   │   │   └── WebSocketConfig.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── CartController.java
│   │   │   │   ├── CartItemsController.java
│   │   │   │   ├── ChatController.java
│   │   │   │   ├── FavoriteProductController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── ProductsController.java
│   │   │   │   ├── SellersController.java
│   │   │   │   ├── StronaController.java
│   │   │   │   ├── UsersController.java
│   │   │   │   └── WarehouseController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── AssemblingOrdersDTO.java
│   │   │   │   ├── ChatMessageDTO.java
│   │   │   │   ├── DeliveryOrdersDTO.java
│   │   │   │   ├── DeliveryPositionOrdersDTO.java
│   │   │   │   ├── ModalWindowDTO.java
│   │   │   │   ├── OrderRequestDTO.java
│   │   │   │   ├── ProductDTO.java
│   │   │   │   └── UsersDTO.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── Address.java
│   │   │   │   ├── CartItems.java
│   │   │   │   ├── Carts.java
│   │   │   │   ├── FavoriteProducts.java
│   │   │   │   ├── OrderItems.java
│   │   │   │   ├── Orders.java
│   │   │   │   ├── Products.java
│   │   │   │   ├── Roles.java
│   │   │   │   └── Users.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── AddressRepository.java
│   │   │   │   ├── CartItemsRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── FavoriteProductRepository.java
│   │   │   │   ├── OrderItemRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── ProductsRepository.java
│   │   │   │   ├── SellersRepository.java
│   │   │   │   ├── UsersRepository.java
│   │   │   │   └── UsersTypeRepository.java
│   │   │   │
│   │   │   ├── services/
│   │   │   │   ├── AddressServices.java
│   │   │   │   ├── CartItemsServices.java
│   │   │   │   ├── CartServices.java
│   │   │   │   ├── CustomUserDetailsServices.java
│   │   │   │   ├── FavoriteProductsServices.java
│   │   │   │   ├── OrderItemService.java
│   │   │   │   ├── OrderServices.java
│   │   │   │   ├── ProductsService.java
│   │   │   │   ├── RedisChatService.java
│   │   │   │   ├── SellersService.java
│   │   │   │   ├── UsersService.java
│   │   │   │   ├── UsersTypeService.java
│   │   │   │   └── WarehouseService.java
│   │   │   │
│   │   │   ├── util/
│   │   │   │   ├── CustomUserDetails.java
│   │   │   │   └── FileUploadUtil.java
│   │   │   │
│   │   │   └── ShopApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── ChatCSS.css
│   │       │   │   ├── ForWarehousePages.css
│   │       │   │   ├── MakingOrderCSS.css
│   │       │   │   ├── Registration.css
│   │       │   │   ├── SellerHomePageCSS.css
│   │       │   │   └── YourShop.css
│   │       │   │
│   │       │   └── js/
│   │       │       ├── assemblingOrders.js
│   │       │       ├── cartItemProd.js
│   │       │       ├── editProduct.js
│   │       │       ├── favoriteprod.js
│   │       │       ├── orderForm.js
│   │       │       └── sellersPages.js
│   │       │
│   │       ├── templates/
│   │       │   ├── assemblingOrders.html
│   │       │   ├── cartItemProduct.html
│   │       │   ├── favoriteProduct.html
│   │       │   ├── login.html
│   │       │   ├── mainPage.html
│   │       │   ├── makingOrder.html
│   │       │   ├── products.html
│   │       │   ├── register.html
│   │       │   └── sellerHomePage.html
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/diplom_proj/shop/
│           └── ShopApplicationTests.java
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
├── .gitignore
└── .gitattributes
```

---

## 📂 Database structure

<p align="center">
  <img src="images/Table.png" width="600"/>
</p>

---

##  Screenshots

### Main page

<p align="center">
  <img src="images/mainPage.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/favoriteProduct.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/cartProduct.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/createOrder.png" width="600" hight="350"/>
</p>

---

### Login page

<p align="center">
  <img src="images/lofin.png" width="600" hight="350"/>
</p>

---

### Register page

<p align="center">
  <img src="images/register.png" width="600" hight="350"/>
</p>

---

### Storekeeper's Page

<p align="center">
  <img src="images/magazMainPage.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/magazAssemblingOrder.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/magazChat.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/magazAddProduct.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/magazShopOrder.png" width="600" hight="350"/>
</p>

---

### Seller's Page

<p align="center">
  <img src="images/spszedOrder.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/spszedOrderItem.png" width="600" hight="350"/>
</p>
<p align="center">
  <img src="images/spszedChat.png" width="600" hight="350"/>
</p>

---


