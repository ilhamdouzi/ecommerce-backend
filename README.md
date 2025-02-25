# 🛒 E-Commerce Application

This is an e-commerce application that provides a comprehensive API for managing products, user authentication (with JWT-based security), shopping carts, and wishlists. The project is built using **Spring Boot** and leverages best practices in design, logging, and security.

## 📚 Table of Contents

- [📖 Overview](#overview)
- [✨ Features](#features)
- [🏛️ Architecture & Best Practices](#architecture--best-practices)
- [🔒 Security](#security)
- [🧪 Testing](#testing)
- [🚀 Running the Project](#running-the-project)

---

## 📖 Overview

This e-commerce backend API allows you to:
- 🛠️ Manage products (CRUD operations).
- 🔐 Authenticate and register users with JWT security.
- 🛒 Manage user shopping carts (add/update/remove products).
- ❤️ Manage user wishlists (add/remove products).

All layers (controller, service, repository) are well-structured, and logging is added for traceability. The code follows Git best practices with atomic commits and conventional commit messages.

---

## ✨ Features

- **📦 Product API:**  
  Create, update, retrieve, and delete products.

- **👤 User Authentication:**  
  Login and registration endpoints using JWT tokens.

- **🛒 Shopping Cart:**  
  Retrieve the cart by user ID, add products, update quantities, and remove items.

- **❤️ Wishlist:**  
  Retrieve the wishlist, add products, and remove items.

- **🔒 Security:**  
  JWT-based authentication with restricted access (only `admin@admin.com` can add/update/delete products).

- **📝 Logging:**  
  Consistent logging with correlation IDs across controllers and services.

---
## 🏛️ Architecture & Best Practices

- **Layered Architecture:**  
  The project follows a clear separation of concerns:
    - **Controller Layer:** Exposes REST endpoints and logs high-level events.
    - **Service Layer:** Contains business logic with detailed debugging logs.
    - **Repository Layer:** Uses Spring Data JPA for database interactions.

- **Immutability & DTOs:**  
  Immutable DTOs (using Java records and Lombok's `@Builder`) are used to transfer data.

- **🔒 Security:**  
  Only authorized users (for example, `admin@admin.com`) can modify products. Authentication and authorization are handled via JWT tokens.

- **📝 Logging & Monitoring:**  
  The application uses SLF4J with Logback, including MDC for correlation IDs, ensuring that each request's logs can be traced end-to-end.

---
## 🔒 Security

- 🔐 JWT-based authentication secures endpoints.
- 🛡️ Sensitive endpoints (like product modification) are restricted to the admin user.
- ✅ Input validation is enforced on DTOs (using Bean Validation annotations).

---

## 🧪 Testing

- **📤 Postman Collections:**  
  All API endpoints have corresponding Postman collections (located in the `/postman` folder). These collections allow you to test each API endpoint.

- **🔄 Newman Integration:**  
  You can run these Postman collections using Newman from the command line. For example:
  ```bash
  newman run postman/Auth_API.postman_collection --env-var baseUrl=http://localhost:8080
  ```
---

# 🚀 Running the Project

## Prerequisites

- ☕ Java 17
- 🛠️ Maven
- 🌱 Spring Boot
- 🗄️ Database (PostgreSQL):
- Ensure a PostgreSQL database named ecommerce_db is available and configured.

## Running Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/ilhamdouzi/ecommerce-backend.git
   ```
   ```bash
   cd ecommerce-backend
   
2. Build the project using Maven:
   ```bash
   mvn clean install

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at 🌐 http://localhost:8080.

## 📤 Postman Collections

-  The Postman collections are exported in the /postman folder.
-  They cover endpoints for Authentication, Product API, Cart, and WishList.
-  Use Newman or Postman GUI to execute these collections.

## ℹ️ Additional Information

1. 🗃️ Data Initialization:

The application uses a data.sql file (located in src/main/resources) to initialize the database with sample products.

2. 📝 Commit Conventions:

The code follows Conventional Commits guidelines, ensuring atomic and clear commit messages.

## 📜 License

This project is licensed under the MIT License.
