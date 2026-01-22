# 🚀 SaaS Subscription Management Platform

A high-performance, enterprise-grade SaaS Subscription Management Platform built with **Spring Boot 3**, **React**, and **Stripe**. This platform is designed to handle complex billing cycles, multiple subscription tiers, and secure automated payments.

## 🌟 Why This Project is "Gold"
Subscription-based models are the backbone of the modern software industry. This project demonstrates:
- **Full-Stack Mastery**: Seamless integration between a robust Java backend and a dynamic React frontend.
- **Financial Integrity**: Secure payment processing with Stripe, including webhook-driven state management.
- **Architectural Excellence**: Decoupled, scalable architecture following industry best practices.
- **Business Logic Complexity**: Managing multi-tiered plans, user roles, and subscription lifecycles.

## 🛠️ Technology Stack
### Backend (The Brain)
- **Java 21** & **Spring Boot 3.4**: High-performance backend framework.
- **Spring Security + JWT**: Stateless authentication for top-tier security.
- **Spring Data JPA + PostgreSQL**: Persistent, reliable data storage.
- **Stripe Java SDK**: Enterprise payment gateway integration.
- **Lombok**: For clean, boilerplate-free code.

### Frontend (The Face)
- **React 19 (Vite)**: Lightning-fast, modern UI development.
- **Tailwind CSS**: Utility-first styling for a sleek, responsive design.
- **React Router 7**: Advanced client-side routing.
- **Axios**: Promised-based HTTP client with interceptors for JWT.

## ✨ Key Features
- **Secure Authentication**: End-to-end user registration and login with encrypted passwords and JWT tokens.
- **Dynamic Pricing**: Fetch real-time plan data from the backend.
- **Stripe Checkout**: One-click subscription flow using Stripe's secure, hosted checkout pages.
- **Webhook Synchronization**: Automated database updates when a user pays, cancels, or changes their plan via Stripe.
- **User Dashboard**: personalized view of current subscription status, plan details, and billing periods.
- **Global Error Handling**: Comprehensive backend exception management for a smooth API experience.

## 🏗️ Architecture & Design Patterns
- **Service Layer Pattern**: Decoupling business logic from controllers.
- **Repository Pattern**: Abstracting data access.
- **DTO Pattern**: Ensuring secure and efficient data transfer between layers and avoiding circular dependency issues.
- **Event-Driven Webhooks**: Decoupled synchronization with external payment providers.

## 🚦 Getting Started

### Prerequisites
- **JDK 21**
- **Node.js 22+**
- **PostgreSQL**
- **Stripe API Keys** (Test Mode)

### Backend Configuration
Update `backend/src/main/resources/application.properties`:
```properties
stripe.api.key=sk_test_...
stripe.webhook.secret=whsec_...
```
Run the backend:
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend Configuration
Install and start:
```bash
cd frontend
npm install
npm run dev
```

## 📊 Database Entities
- `User`: Handles account details and Stripe customer mapping.
- `Plan`: Stores tier information (Basic, Pro, Enterprise).
- `Subscription`: Tracks the lifecycle of a user's plan.
- `Invoice`: Keeps a record of payments and receipt links.

---
*Developed with precision for a 6-8 LPA career trajectory. This isn't just a project; it's a statement of engineering capability.*
