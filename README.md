# Order Management System

A modular Java Spring Boot application for managing orders and their items.

---

## Submodules

### 1. `order-svc`

**Purpose:**  
Handles order creation, management, and retrieval.

**Main Features:**
- Create new orders
- Retrieve order details
- Update order status
- List all orders

**API Endpoints:**
- `POST /orders` — Create a new order
- `GET /orders/{id}` — Get order by ID
- `PUT /orders/{id}` — Update order
- `GET /orders` — List all orders

**Dependencies:**
- Spring Boot
- SQL database
- Gradle

---

### 2. `order-item-svc`

**Purpose:**  
Manages items within an order.

**Main Features:**
- Add items to an order
- Remove items from an order
- Update item quantity
- List items in an order

**API Endpoints:**
- `POST /orders/{orderId}/items` — Add item to order
- `DELETE /orders/{orderId}/items/{itemId}` — Remove item
- `PUT /orders/{orderId}/items/{itemId}` — Update item quantity
- `GET /orders/{orderId}/items` — List items in order

**Dependencies:**
- Spring Boot
- SQL database
- Gradle

---

## Getting Started

1. Clone the repository.
2. Build with Gradle:  
   `./gradlew build`
3. Run each service:  
   `./gradlew :order-svc:bootRun`  
   `./gradlew :order-item-svc:bootRun`

---

## License

Its personal project.

