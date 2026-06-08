# Delivery Chat System

## 1. Project Overview

Delivery Chat System is a Spring Boot-based web application that simulates a delivery communication system between customers and delivery drivers.

The project is designed around a delivery workflow where a customer places an order, a delivery is assigned to a driver, and the customer and driver can communicate through delivery-specific messages.

This project was built to practice backend development using Spring Boot, MyBatis, PostgreSQL, and REST APIs, while following a structure similar to an enterprise system integration project.

---

## 2. Main Features

* View all customers
* View all delivery drivers
* View all deliveries
* View deliveries assigned to a specific driver
* View detailed delivery information using customer and driver data
* View messages for a specific delivery
* Send a message for a selected delivery
* Update delivery status from the driver web page
* Display delivery data and messages on a simple web interface

---

## 3. Tech Stack

### Backend

* Java
* Spring Boot
* Spring Web
* MyBatis
* JDBC

### Database

* PostgreSQL

### Frontend

* HTML
* JavaScript
* Fetch API

### Tools

* VS Code
* DBeaver
* Gradle

---

## 4. System Structure

```text
Browser / Web Page
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

* **Controller** handles HTTP requests from the client.
* **Service** contains business logic.
* **Mapper** executes SQL queries using MyBatis.
* **Database** stores customers, drivers, deliveries, and messages.

---

## 5. Database Tables

### customers

Stores customer information.

```text
id
name
phone_number
```

### drivers

Stores delivery driver information.

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

### messages

Stores messages exchanged between customers and drivers for each delivery.

```text
id
delivery_id
sender_type
sender_id
content
created_at
```

---

## 6. API Endpoints

### Customer APIs

```text
GET /customers
```

Returns all customers.

---

### Driver APIs

```text
GET /drivers
```

Returns all delivery drivers.

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
GET /deliveries/{deliveryId}
```

Returns detailed delivery information, including customer and driver details.

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

Returns all messages for a specific delivery.

```text
POST /deliveries/{deliveryId}/messages
```

Sends and saves a new message for a specific delivery.

Example request body:

```json
{
  "senderType": "DRIVER",
  "senderId": 1,
  "content": "30분 뒤 도착 예정입니다."
}
```

---

## 7. Web Page

The project includes a simple driver web page:

```text
/driver-deliveries.html
```

Main functions:

* Enter a driver ID
* View deliveries assigned to that driver
* Select a delivery
* View messages for the selected delivery
* Send a new message
* Update delivery status

---

## 8. Example Workflow

1. A driver enters their driver ID.
2. The system displays deliveries assigned to that driver.
3. The driver selects a delivery.
4. The system displays delivery-related messages.
5. The driver sends a message to the customer.
6. The message is saved in PostgreSQL.
7. The message list refreshes on the web page.
8. The driver updates the delivery status.

---

## 9. How to Run

### 1. Start PostgreSQL

Make sure PostgreSQL is running locally.

```bash
brew services start postgresql@15
```

### 2. Run the Spring Boot application

```bash
./gradlew bootRun
```

### 3. Open the web page

```text
http://localhost:8080/driver-deliveries.html
```

---

## 10. Future Improvements

* Add WebSocket for real-time chat updates
* Add login and authentication for drivers and admins
* Add role-based access control using Spring Security
* Improve frontend design
* Add validation for request data
* Add error handling for invalid delivery IDs or driver IDs
* Add separate customer-facing chat page
* Add order and product tables to better reflect a real delivery system

---

## 11. Current Status

The current version supports basic delivery management and message communication using REST APIs.

Implemented features include:

* Customer, driver, delivery, and message database integration
* MyBatis-based SQL queries
* Driver-specific delivery list
* Delivery-specific message list
* Message sending through the web page
* Delivery status update through the web page
