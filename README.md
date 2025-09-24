# 🛒 E-Commerce Backend

A Spring Boot–based backend for an e-commerce application, featuring secure authentication, cart management, order processing, and Razorpay payment integration.

---

## 🚀 Features
- **Authentication**
  - JWT-based login & registration
  - Google login via **OAuth2.0**

- **Product Management**
  - Add, update, delete, and fetch products
  - View product details & listings

- **Cart & Orders**
  - Add multiple instances of a product to the cart
  - Manage cart items (increase/decrease/remove)
  - Place orders & view order history

- **Payments**
  - Razorpay integration with the amount confirmation page

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot, Spring Security, Spring Data JPA  
- **Database:** MySQL / PostgreSQL (Default: MySQL) 
- **Authentication:** JWT, OAuth2.0 (Google)  
- **Payments:** Razorpay API  

---

## 📂 Project Structure
```plaintext
src/main/java/com/ecommerce/product/service
├── config (Configuration for JWT & OAuth2 configurations)
├── controller (REST API controllers)
├── dtos (Data transfer object)
├── entity
├── exception (Global exceptions)
├── mappers 
├── repository (JPA repositories)
├── service (Business logic)
├── security (Security Config)
```

---

## ⚙️ Setup & Installation
1. Clone the repository  
   ```bash
   git clone https://github.com/Rohit-Dhiman-01/practice.product.service
   cd practice.product.service
2. Create a database named:
    ```bash
    CREATE DATABASE store_api;
3. Configure environment variables (required):
    ```env
    JWT_SECRET=your_jwt_secret
    GOOGLE_CLIENT_ID=your_google_client_id
    GOOGLE_CLIENT_SECRET=your_google_client_secret
    DATABASE_USERNAME=your_db_username
    DATABASE_PASSWORD=your_db_password



## ▶️ Running the Project
This is a Maven project. To start the application, run:
    
     ./mvnw spring-boot:run
If you're on Windows:

    mvnw.cmd spring-boot:run
Once running, the application will be available at:
    
    http://localhost:8080

---

## 📚 API Documentation
Swagger UI is available at:

    http://localhost:8080/swagger-ui.html
