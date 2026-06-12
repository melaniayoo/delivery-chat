# Delivery Chat System

## 1. Project Overview

Delivery Chat System is a Spring Boot-based web application that simulates a delivery communication system between customers, delivery drivers, and an administrator.

The system follows a delivery workflow where a customer creates a delivery request, the system automatically assigns the delivery to an available driver, and the customer and driver can communicate through a delivery-specific real-time chat room.

This project was built to practice backend development using Spring Boot, MyBatis, PostgreSQL, Spring Security, REST APIs, and WebSocket communication, while following a layered architecture similar to an enterprise system integration project.

---

## 2. Main Features

### Authentication and Authorization

* Customer registration
* Admin-created driver accounts
* Database-backed login using Spring Security
* BCrypt password encryption
* Role-based access control for ADMIN, CUSTOMER, and DRIVER users
* Role-specific page navigation
* Current logged-in user display
* Logout functionality

### Delivery Management

* Customers can create delivery requests
* Delivery drivers are automatically assigned based on current delivery count
* Customers can view only their own deliveries
* Drivers can view only deliveries assigned to them
* Drivers can update delivery status
* Admin can view customers, drivers, and deliveries

### Real-Time Chat

* Customers and drivers can communicate through delivery-specific chat rooms
* WebSocket and STOMP are used for real-time message delivery
* Messages are saved in PostgreSQL
* Existing message history can be loaded for each delivery
* Messages include sender information and timestamp

### Web Interface

* Login and customer registration page
* Customer delivery request page
* Customer delivery tracking and chat page
* Driver delivery management and chat page
* Admin dashboard for managing drivers and viewing system data

---

## 3. Tech Stack

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Security
* Spring WebSocket
* MyBatis
* JDBC

### Database

* PostgreSQL

### Frontend

* HTML
* CSS
* JavaScript
* Fetch API
* WebSocket
* STOMP.js
* SockJS

### Tools

* DBeaver
* Gradle

---

## 4. System Architecture

```text
Browser / Web Pages
        ↓
Spring Security
        ↓
Controller
        ↓
Service
        ↓
Mapper (MyBatis)
        ↓
PostgreSQL Database
```

The project follows a layered architecture:

* **Static HTML/CSS/JavaScript** provides the browser-based user interface.
* **Spring Security** handles login, logout, authentication, and role-based authorization.
* **Controller** handles HTTP requests and WebSocket messages.
* **Service** contains business logic such as registration, driver assignment, and message handling.
* **Mapper** executes SQL queries using MyBatis.
* **PostgreSQL** stores users, customers, drivers, deliveries, and messages.

---

## 5. Project Structure

```text
src/main/java/com/example/delivery_chat
├── config
│   ├── SecurityConfig.java
│   └── WebSocketConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── ChatController.java
│   ├── CustomerController.java
│   ├── DeliveryController.java
│   ├── DriverController.java
│   └── MessageController.java
│
├── dto
│   ├── AuthMeResponse.java
│   ├── ChatMessage.java
│   ├── CustomerRequest.java
│   ├── DeliveryDetailResponse.java
│   ├── DeliveryRequest.java
│   ├── DeliveryStatusUpdateRequest.java
│   ├── DriverDeliveryCountResponse.java
│   ├── DriverRequest.java
│   ├── MessageRequest.java
│   └── RegisterRequest.java
│
├── entity
│   ├── AppUser.java
│   ├── Customer.java
│   ├── Delivery.java
│   ├── Driver.java
│   └── Message.java
│
├── mapper
│   ├── AppUserMapper.java
│   ├── CustomerMapper.java
│   ├── DeliveryMapper.java
│   ├── DriverMapper.java
│   └── MessageMapper.java
│
├── service
│   ├── AuthService.java
│   ├── CustomUserDetailsService.java
│   ├── CustomerService.java
│   ├── DeliveryService.java
│   ├── DriverService.java
│   └── MessageService.java
│
└── DeliveryChatApplication.java
```

```text
src/main/resources/static
├── admin.html
├── customer-delivery.html
├── customer-order.html
├── driver-deliveries.html
├── login.html
└── style.css
```

---

## 6. Database Tables

### app_users

Stores login accounts and role information.

```text
id
username
password
role
customer_id
driver_id
```

Roles:

```text
ADMIN
CUSTOMER
DRIVER
```

### customers

Stores customer profile information.

```text
id
name
phone_number
```

### drivers

Stores delivery driver profile information.

```text
id
name
phone_number
```

### deliveries

Stores delivery information and connects customers with drivers.

```text
id
customer_id
driver_id
delivery_address
status
```

Example delivery statuses:

```text
READY
OUT_FOR_DELIVERY
DELIVERED
```

### messages

Stores chat messages exchanged between customers and drivers for each delivery.

```text
id
delivery_id
sender_type
sender_id
content
created_at
```

---

## 7. Web Pages

### `/login.html`

Login and customer registration page.

Main functions:

* Login for admin, customer, and driver users
* Customer self-registration
* Redirect users to role-specific pages after login

### `/admin.html`

Admin dashboard.

Main functions:

* View customers
* View drivers
* View deliveries
* Create driver accounts with username and password

### `/customer-order.html`

Customer delivery request page.

Main functions:

* Display the logged-in customer account
* Create a new delivery request
* Automatically use the logged-in customer ID
* Automatically assign a driver on the server side

### `/customer-delivery.html`

Customer delivery tracking and chat page.

Main functions:

* View deliveries created by the logged-in customer
* View delivery details
* View message history
* Send real-time messages to the assigned driver

### `/driver-deliveries.html`

Driver delivery management and chat page.

Main functions:

* View deliveries assigned to the logged-in driver
* View delivery details
* View message history
* Send real-time messages to the customer
* Update delivery status

---

## 8. API Endpoints

### Authentication APIs

```text
POST /auth/register/customer
```

Registers a new customer account.

Example request body:

```json
{
  "username": "melania",
  "password": "pass123",
  "name": "Melania",
  "phoneNumber": "010-0000-0000"
}
```

```text
GET /auth/me
```

Returns the currently logged-in user.

Example response:

```json
{
  "username": "melania",
  "role": "CUSTOMER",
  "customerId": 1,
  "driverId": null
}
```

---

### Customer APIs

```text
GET /customers
```

Returns all customers.

```text
POST /customers
```

Creates a customer. This API is mainly used internally or for admin/testing purposes because customer creation is normally handled through registration.

---

### Driver APIs

```text
GET /drivers
```

Returns all delivery drivers.

```text
POST /drivers
```

Creates a new driver and a linked login account. This is intended for admin use.

Example request body:

```json
{
  "username": "driverkim",
  "password": "driver123",
  "name": "김기사",
  "phoneNumber": "010-1111-1111"
}
```

```text
GET /drivers/{driverId}/deliveries
```

Returns deliveries assigned to a specific driver.

---

### Delivery APIs

```text
GET /deliveries
```

Returns all deliveries.

```text
POST /deliveries
```

Creates a new delivery request and automatically assigns a driver.

Example request body:

```json
{
  "customerId": 1,
  "deliveryAddress": "서울시 강남구 테헤란로 123"
}
```

```text
GET /deliveries/{deliveryId}
```

Returns detailed delivery information, including customer and driver details.

```text
GET /customers/{customerId}/deliveries
```

Returns deliveries created by a specific customer.

```text
PATCH /deliveries/{deliveryId}/status
```

Updates the status of a delivery.

Example request body:

```json
{
  "status": "DELIVERED"
}
```

---

### Message APIs

```text
GET /deliveries/{deliveryId}/messages
```

Returns all saved messages for a specific delivery.

```text
POST /deliveries/{deliveryId}/messages
```

Sends and saves a new message using REST API.

Example request body:

```json
{
  "senderType": "DRIVER",
  "senderId": 1,
  "content": "30분 뒤 도착 예정입니다."
}
```

---

## 9. WebSocket Communication

The project uses WebSocket and STOMP for real-time chat.

### WebSocket Endpoint

```text
/ws
```

### Client-to-Server Message Mapping

```text
/app/chat.send
```

### Server-to-Client Topic

```text
/topic/deliveries/{deliveryId}
```

When a customer or driver sends a chat message, the browser sends it to:

```text
/app/chat.send
```

The server saves the message in PostgreSQL and broadcasts it to:

```text
/topic/deliveries/{deliveryId}
```

Any customer or driver currently viewing that delivery chat receives the message immediately.

Example WebSocket message:

```json
{
  "deliveryId": 1,
  "senderType": "CUSTOMER",
  "senderId": 1,
  "content": "안녕하세요, 언제 도착하시나요?"
}
```

---

## 10. Example Workflow

### Customer Flow

1. A customer registers on `/login.html`.
2. The customer logs in.
3. The customer is redirected to `/customer-order.html`.
4. The customer creates a delivery request.
5. The system automatically assigns a driver.
6. The customer opens `/customer-delivery.html`.
7. The customer views their delivery and sends a real-time message to the driver.

### Driver Flow

1. An admin creates a driver account from `/admin.html`.
2. The driver logs in using the assigned username and password.
3. The driver is redirected to `/driver-deliveries.html`.
4. The driver views assigned deliveries.
5. The driver opens a delivery chat.
6. The driver sends real-time messages to the customer.
7. The driver updates the delivery status.

### Admin Flow

1. The admin logs in.
2. The admin opens `/admin.html`.
3. The admin views customers, drivers, and deliveries.
4. The admin creates new driver accounts.

---

## 11. How to Run

### 1. Start PostgreSQL

Make sure PostgreSQL is running locally.

```bash
brew services start postgresql@15
```

### 2. Create the database

```bash
createdb delivery_chat
```

### 3. Run the Spring Boot application

```bash
./gradlew bootRun
```

### 4. Open the application

```text
http://localhost:8080/login.html
```

---

## 12. Default Admin Account

When the application starts, a default admin account is created if it does not already exist.

```text
username: admin
password: admin123
role: ADMIN
```

The admin can create delivery driver accounts from the admin dashboard.

---

## 13. Current Status

The current version supports:

* Customer registration
* Admin-created driver accounts
* Database-backed login
* Role-based page access
* Role-specific navigation
* Automatic driver assignment
* Customer-specific delivery views
* Driver-specific delivery views
* Delivery status updates
* Real-time WebSocket chat
* Message persistence in PostgreSQL
* Admin dashboard for system data

---

## 14. Future Improvements

* Add stronger validation for request data
* Improve error handling and user-facing error messages
* Add CSRF protection for production use
* Add custom styling for chat bubbles
* Add order and product tables to better reflect a real delivery system
* Add pagination or search for admin data tables
* Add deployment configuration
* Add automated tests
