# MicroService_notificationService_Kafka

# 🚀 Notification API Service

A production-ready, multi-tenant Notification API built using Spring
Boot.\
This project demonstrates clean architecture, centralized exception
handling, request tracing using MDC, and thread-safe context management.

------------------------------------------------------------------------

## 📌 Project Overview

This service provides REST APIs to manage notifications in a
**multi-tenant environment**.

Each request:

-   Must contain a `X-Tenant-Id`
-   Is assigned a unique `X-Request-Id`
-   Is validated via a custom filter
-   Is processed safely using ThreadLocal context
-   Returns structured error responses

------------------------------------------------------------------------

## 🏗 Architecture

Client\
↓\
NotificationAuthFilter\
↓\
Controller\
↓\
Service Layer\
↓\
Repository\
↓\
Database

------------------------------------------------------------------------

## 🏢 Multi-Tenant Design

Tenant is identified using request header:

`X-Tenant-Id`

Tenant context is stored using:

`NotificationContextHolder (ThreadLocal)`

This ensures:

-   Tenant isolation per request\
-   No cross-tenant data leakage\
-   Thread-safe request processing

------------------------------------------------------------------------

## 🔄 Request Flow

1.  Client sends request\
2.  Filter validates tenant header\
3.  Request ID generated\
4.  Tenant context stored\
5.  Controller processes request\
6.  Service executes business logic\
7.  Response returned\
8.  Context cleared in finally block

------------------------------------------------------------------------

## 🔐 Filter Layer

### NotificationAuthFilter

Extends: `OncePerRequestFilter`

### Responsibilities:

-   Validate `/api` routes\
-   Validate `X-Tenant-Id`\
-   Generate unique request ID\
-   Store tenant in ThreadLocal\
-   Ensure cleanup using try-finally

Core Logic:

``` java
try {
    filterChain.doFilter(request, response);
} finally {
    NotificationContextHolder.clear();
}
```

------------------------------------------------------------------------

## ❗ Exception Handling

Custom exceptions:

-   AbstractException\
-   ValidationException\
-   ResourceNotFoundException

Handled centrally using `@GlobalExceptionHandler`.

### Example Error Response

``` json
{
  "errorCode": "VALIDATION_FAILED",
  "message": "Email is required",
  "status": 400
}
```

------------------------------------------------------------------------

## 🧵 Thread Safety

Tenant data is stored using ThreadLocal.

Because Spring uses thread pools, it is critical to clear context after
request:

``` java
finally {
    NotificationContextHolder.clear();
}
```

This prevents:

-   Thread reuse issues\
-   Tenant data leakage\
-   Memory leaks

------------------------------------------------------------------------

## 🪵 Logging & MDC

Each request generates `X-Request-Id`.

Stored using:

``` java
MDC.put("X_REQUEST_ID", requestId);
```

Log pattern example:

`%X{X_REQUEST_ID}`

Benefits:

-   Easy debugging\
-   Log tracing per request\
-   Correlation across services

------------------------------------------------------------------------

## 📂 Project Structure

com.notification.api\
│\
├── controllers\
├── services\
├── repositories\
├── filters\
│ └── NotificationAuthFilter\
├── models\
│ ├── context\
│ │ ├── NotificationContext\
│ │ └── NotificationContextHolder\
├── exceptions\
│ ├── AbstractException\
│ ├── ValidationException\
│ ├── ResourceNotFoundException\
│ └── GlobalExceptionHandler\
├── utils\
│ └── CommanUtils\
└── constants

------------------------------------------------------------------------

## 🧪 How to Run

### Prerequisites

-   Java 17+\
-   Maven\
-   Database (MySQL / MongoDB based on configuration)

### Build and Run

``` bash
mvn clean install
mvn spring-boot:run
```

Application will start on:

http://localhost:8080

------------------------------------------------------------------------

## 🛠 Best Practices Followed

-   SOLID principles\
-   Clean layered architecture\
-   Centralized exception handling\
-   Thread-safe context management\
-   MDC-based structured logging\
-   Production-safe filter lifecycle handling

------------------------------------------------------------------------

## 🏁 Conclusion

This project demonstrates an enterprise-ready backend system with:

-   Multi-tenant architecture\
-   Thread safety using ThreadLocal\
-   Centralized exception handling\
-   Request-level tracing with MDC\
-   Clean and maintainable code structure

------------------------------------------------------------------------

Author: Jai Sharma\
Tech Stack: Spring Boot, Java, REST APIs, ThreadLocal, MDC Logging
