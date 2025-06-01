# Java-Assignment

A robust REST API for task management with role-based authentication, built using Spring Boot and PostgreSQL.
## 📋 Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Database Design](#database-design)
- [Project Structure](#project-structure)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Security](#security)
- [Technologies Used](#technologies-used)

## 🚀 Overview
This Task Management System provides a comprehensive solution for managing tasks with role-based access control. The system supports two types of users: **Admins** (who can create and assign tasks) and **Users** (who receive task assignments).
### Key Capabilities
- **User Authentication**: Secure JWT-based authentication for both users and admins
- **Task Management**: Complete CRUD operations for tasks
- **Role-Based Access**: Differentiated permissions for admins and regular users
- **User Management**: Admin capability to manage user accounts
- **RESTful API**: Well-structured REST endpoints with proper HTTP status codes

## 🏗️ Architecture
The application follows a layered architecture pattern:
``` 
┌─────────────────┐
│   Controllers   │  ← REST API Layer
├─────────────────┤
│    Services     │  ← Business Logic Layer
├─────────────────┤
│  Repositories   │  ← Data Access Layer
├─────────────────┤
│    Database     │  ← PostgreSQL
└─────────────────┘
```
### Design Patterns Used
- **Repository Pattern**: For data access abstraction
- **DTO Pattern**: For data transfer between layers
- **Service Layer Pattern**: For business logic encapsulation
- **Dependency Injection**: Using Spring's IoC container

## 🗄️ Database Design
### Entity Relationship Diagram
``` 
┌─────────────┐         ┌─────────────┐         ┌─────────────┐
│    Admin    │         │    Task     │         │   AppUser   │
├─────────────┤         ├─────────────┤         ├─────────────┤
│ id (PK)     │────────▶│ id (PK)     │◀────────│ id (PK)     │
│ username    │   1:N   │ title       │   N:1   │ username    │
│ email       │         │ description │         │ email       │
│ password    │         │ assigned_by │         │ password    │
│ created_at  │         │ user_id     │         │ created_at  │
│ updated_at  │         │ created_at  │         │ updated_at  │
└─────────────┘         │ updated_at  │         └─────────────┘
                        └─────────────┘
```
### Database Tables
#### **admin** Table

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| username | VARCHAR(20) | NOT NULL, UNIQUE |
| email | VARCHAR(50) | NOT NULL, UNIQUE |
| password | VARCHAR(60) | NOT NULL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
#### **app_user** Table

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| username | VARCHAR(20) | NOT NULL, UNIQUE |
| email | VARCHAR(50) | NOT NULL, UNIQUE |
| password | VARCHAR(60) | NOT NULL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
#### **tasks** Table

| Column | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| title | VARCHAR(255) | NOT NULL |
| description | TEXT | NULLABLE |
| user_id | BIGINT | FOREIGN KEY → app_user(id) |
| assigned_by | BIGINT | FOREIGN KEY → admin(id) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
### Relationships
- **Admin → Tasks**: One-to-Many (One admin can create multiple tasks)
- **User → Tasks**: One-to-Many (One user can be assigned multiple tasks)
- **Task → Admin**: Many-to-One (Each task is created by one admin)
- **Task → User**: Many-to-One (Each task is assigned to one user)

## 📁 Project Structure
``` 
src/main/java/com/example/java_assignment/
├── 📁 config/
│   └── SecurityConfig.java           # Spring Security configuration
├── 📁 controller/
│   ├── AuthController.java           # User authentication endpoints
│   ├── AdminAuthController.java      # Admin authentication & user management
│   └── TaskController.java           # Task management endpoints
├── 📁 dto/
│   ├── JwtResponse.java              # JWT response wrapper
│   ├── LoginRequest.java             # Login request DTO
│   ├── RegistrationDTO.java          # User registration DTO
│   ├── TaskRequestDTO.java           # Task creation/update DTO
│   └── TaskResponseDTO.java          # Task response DTO
├── 📁 exception/
│   └── ResourceNotFoundException.java # Custom exception handling
├── 📁 mapper/
│   └── TaskMapper.java               # Entity-DTO mapping utility
├── 📁 model/
│   ├── Admin.java                    # Admin entity
│   ├── AppUser.java                  # User entity
│   └── Task.java                     # Task entity
├── 📁 repository/
│   ├── AdminRepository.java          # Admin data access
│   ├── AppUserRepository.java        # User data access
│   └── TaskRepository.java           # Task data access
├── 📁 security/
│   ├── AuthUtils.java                # Authentication utilities
│   ├── JwtAuthenticationFilter.java  # JWT filter
│   ├── JwtUtils.java                 # JWT token utilities
│   └── UserDetailsImpl.java          # Spring Security user details
├── 📁 service/
│   ├── AdminService.java             # Admin service interface
│   ├── AppUserService.java           # User service interface
│   ├── TaskService.java              # Task service interface
│   └── 📁 implementation/
│       ├── AdminServiceImpl.java     # Admin business logic
│       ├── AppUserServiceImpl.java   # User business logic
│       ├── TaskServiceImpl.java      # Task business logic
│       └── UserDetailsServiceImpl.java # Spring Security service
└── JavaAssignmentApplication.java    # Spring Boot main class
```
## ✨ Features
### 🔐 Authentication & Authorization
- JWT-based stateless authentication
- Role-based access control (Admin/User)
- Secure password hashing with BCrypt
- Token expiration handling

### 👥 User Management
- User registration and login
- Admin-only user management capabilities
- Unique username and email validation
- Secure password storage

### 📋 Task Management
- Create, read, update, and delete tasks
- Task assignment by admins to users
- Ownership validation for task operations
- Comprehensive task details tracking

### 🛡️ Security Features
- CSRF protection disabled for API usage
- CORS configuration for cross-origin requests
- Input validation using Jakarta validation
- Exception handling with proper HTTP status codes

## 🔌 API Endpoints
### Authentication Endpoints
#### User Authentication
``` http
POST /api/auth/register
POST /api/auth/login
```
#### Admin Authentication
``` http
POST /api/admin/auth/login
```
### User Management (Admin Only)
``` http
GET    /api/admin/users        # List all users
DELETE /api/admin/users/{id}   # Delete user by ID
```
### Task Management
``` http
GET    /api/tasks              # Get all tasks
POST   /api/tasks              # Create new task
PUT    /api/tasks/{id}         # Update task
DELETE /api/tasks/{id}         # Delete task
```
### Example Request/Response
#### Create Task
**Request:**
``` json
POST /api/tasks
{
  "title": "Complete project documentation",
  "description": "Write comprehensive README and API docs",
  "userId": 1
}
```
**Response:**
``` json
{
  "id": 1,
  "title": "Complete project documentation",
  "description": "Write comprehensive README and API docs",
  "assignedToUsername": "john_doe",
  "assignedByUsername": "admin_user",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```
## 🚀 Getting Started
### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

### Installation
1. **Clone the repository**
``` bash
git clone https://github.com/yourusername/task-management-system.git
cd task-management-system
```
1. **Setup PostgreSQL Database**
``` sql
CREATE DATABASE task_management;
CREATE USER task_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE task_management TO task_user;
```
1. **Configure Application Properties**
``` properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=task_user
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your-256-bit-secret-key

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
1. **Build and Run**
``` bash
mvn clean install
mvn spring-boot:run
```
1. **Access the Application**

- API Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## ⚙️ Configuration
### Environment Variables
For production deployment, use environment variables:
``` bash
export DB_URL=jdbc:postgresql://localhost:5432/task_management
export DB_USERNAME=task_user
export DB_PASSWORD=your_password
export JWT_SECRET=your-256-bit-secret-key
```
### Application Profiles
- **Development**: `application-dev.properties`
- **Production**: `application-prod.properties`
- **Testing**: `application-test.properties`

## 🔒 Security
### Authentication Flow
1. User/Admin provides credentials
2. System validates credentials
3. JWT token generated and returned
4. Client includes token in Authorization header
5. Server validates token for each request

### Security Headers
- JWT tokens expire after 24 hours
- CORS configured for cross-origin requests
- Input validation on all endpoints
- SQL injection prevention through JPA

### Password Security
- BCrypt hashing with salt
- Minimum security requirements enforced
- Passwords never returned in API responses

## 🛠️ Technologies Used
### Backend Framework
- **Spring Boot 3.3.5** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Spring Web** - REST API development

### Database
- **PostgreSQL** - Primary database
- **Hibernate** - ORM framework

### Security
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing

### Development Tools
- **Lombok** - Boilerplate code reduction
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Dependency management
- **Spring Boot DevTools** - Development utilities

### Validation & Documentation
- **Jakarta Validation** - Input validation
- **Swagger/OpenAPI 3** - API documentation

## 📝 API Documentation
Once the application is running, visit `http://localhost:8080/swagger-ui.html` for interactive API documentation.
