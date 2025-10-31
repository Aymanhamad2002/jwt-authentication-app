# 🛡️ JWT Authentication App

A production-ready **Spring Boot** application demonstrating secure **JWT-based authentication and authorization** with **refresh token** functionality for REST APIs.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 📘 Overview

This application is built with **Spring Boot**, **Spring Security**, and **MySQL**, implementing a complete JWT authentication system with the following capabilities:

- ✅ User registration and authentication via username and password
- ✅ JWT **access token** generation on successful login
- ✅ **Refresh token** mechanism for seamless session renewal
- ✅ Request filtering and validation of JWT tokens
- ✅ Secure protected endpoints with role-based authorization
- ✅ Persistent user storage with **MySQL database**
- ✅ Token blacklisting/revocation support (optional enhancement)

Perfect for developers looking to implement **stateless, token-based authentication** in production-grade Spring Boot applications.

---

## 🚀 Key Features

| Feature | Description |
|---------|-------------|
| **JWT Access Tokens** | Short-lived tokens for API authentication |
| **Refresh Tokens** | Long-lived tokens to obtain new access tokens without re-login |
| **MySQL Integration** | Persistent data storage for users and tokens |
| **Password Encryption** | BCrypt hashing for secure password storage |
| **Role-Based Access Control** | Fine-grained authorization with user roles |
| **Request Filtering** | Automatic JWT validation on protected routes |
| **Token Expiration** | Configurable expiration times for security |
| **Clean Architecture** | Well-structured, modular codebase |

---

## 🛠️ Technologies Stack

| Layer | Technology |
|:------|:-----------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security 6, JWT (JSON Web Tokens) |
| **Database** | MySQL 8.0+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven |
| **Testing** | JUnit 5, Mockito |

---

## 📂 Project Structure

```
jwt-authentication-app/
├── src/
│   ├── main/
│   │   ├── java/com/backendProject/jwtAuthentication/
│   │   │   ├── config/           # Security & JWT configuration
│   │   │   ├── controller/       # REST API endpoints
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── entity/           # JPA entities (User, Role, RefreshToken)
│   │   │   ├── repository/       # Database repositories
│   │   │   ├── security/         # JWT utilities, filters, authentication
│   │   │   ├── service/          # Business logic layer
│   │   │   └── exception/        # Custom exception handlers
│   │   └── resources/
│   │       ├── application.properties   # App configuration
│   │       └── application-dev.properties
│   └── test/
│       └── java/                 # Unit and integration tests
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

### Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher installed
- **MySQL 8.0+** running locally or remotely
- **Maven 3.6+** for dependency management

### Database Setup

1. Create a MySQL database:

```sql
CREATE DATABASE jwt_auth_db;
```

2. Update `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_auth_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
jwt.secret=YourSuperSecretKeyMinimum256BitsForHS256Algorithm
jwt.access-token.expiration=900000      # 15 minutes (in milliseconds)
jwt.refresh-token.expiration=604800000  # 7 days (in milliseconds)
```

> ⚠️ **Security Warning**: Never commit real secrets to public repositories. Use environment variables or external configuration management (e.g., Spring Cloud Config, AWS Secrets Manager) in production.

---

## 🔐 API Endpoints

### Authentication Endpoints

#### 1. Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "user@example.com",
  "password": "SecurePassword123"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "username": "newuser"
}
```

#### 2. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "newuser",
  "password": "SecurePassword123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "d45f6c8b-3a2e-4f8c-9b1a-7d6e5f4c3b2a",
  "tokenType": "Bearer",
  "expiresIn": 900000
}
```

#### 3. Refresh Access Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "d45f6c8b-3a2e-4f8c-9b1a-7d6e5f4c3b2a"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "new-refresh-token-uuid",
  "tokenType": "Bearer",
  "expiresIn": 900000
}
```

#### 4. Logout
```http
POST /api/auth/logout
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "refreshToken": "d45f6c8b-3a2e-4f8c-9b1a-7d6e5f4c3b2a"
}
```

### Protected Endpoints

#### User Profile
```http
GET /api/user/profile
Authorization: Bearer <access_token>
```

**Response:**
```json
{
  "id": 1,
  "username": "newuser",
  "email": "user@example.com",
  "roles": ["ROLE_USER"]
}
```

#### Admin Dashboard (Admin Only)
```http
GET /api/admin/dashboard
Authorization: Bearer <admin_access_token>
```

---

## 🏃 Running the Application

### Method 1: Using Maven

1. **Clone the repository:**
```bash
git clone https://github.com/Aymanhamad2002/jwt-authentication-app.git
cd jwt-authentication-app
```

2. **Configure the database** (see Configuration section above)

3. **Build the project:**
```bash
mvn clean install
```

4. **Run the application:**
```bash
mvn spring-boot:run
```

5. **Access the API:**
```
http://localhost:8080
```

### Method 2: Using JAR

```bash
mvn clean package
java -jar target/jwt-authentication-app-0.0.1-SNAPSHOT.jar
```

---

## 🧪 Testing with cURL

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "Test1234!"
  }'
```

### Login and get tokens
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "Test1234!"
  }'
```

### Access protected endpoint
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Refresh the access token
```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'
```

---

## 🔍 How It Works

### 1. User Registration
- User submits credentials via `/api/auth/register`
- Password is hashed using **BCrypt**
- User details are stored in MySQL database

### 2. User Login
- User submits credentials via `/api/auth/login`
- Server validates credentials against database
- On success, server generates:
  - **Access Token** (short-lived, e.g., 15 minutes)
  - **Refresh Token** (long-lived, e.g., 7 days)
- Both tokens are returned to the client

### 3. Accessing Protected Resources
- Client includes access token in `Authorization: Bearer <token>` header
- **JWT Filter** intercepts request and validates token:
  - Verifies signature
  - Checks expiration
  - Extracts user details
- If valid, request proceeds to controller

### 4. Token Refresh Flow
- When access token expires, client sends refresh token to `/api/auth/refresh`
- Server validates refresh token against database
- If valid, server issues a new access token (and optionally a new refresh token)
- This allows seamless authentication without re-entering credentials

### 5. Logout
- Client sends refresh token to `/api/auth/logout`
- Server invalidates/deletes the refresh token from database
- Prevents further token refresh requests

---

## 🔐 Security Best Practices Implemented

- ✅ **Password Hashing**: BCrypt with salt rounds
- ✅ **JWT Signing**: HMAC SHA-256 algorithm
- ✅ **Token Expiration**: Short-lived access tokens, longer refresh tokens
- ✅ **Secure Headers**: CORS, CSRF protection configured
- ✅ **SQL Injection Prevention**: Parameterized queries via JPA
- ✅ **Role-Based Access**: Method-level security annotations
- ✅ **Token Storage**: Refresh tokens stored securely in database

---

## 📚 Learning Outcomes

By studying this project, you will understand:

- How to implement **JWT authentication** in Spring Boot
- How **refresh tokens** work and why they're important
- How to integrate **Spring Security** with custom filters
- How to design a **stateless authentication** architecture
- How to secure REST APIs without server-side sessions
- How to work with **MySQL** and **Spring Data JPA**
- How to structure a professional Spring Boot application

---

## 🚧 Future Enhancements

- [ ] Add **email verification** on registration
- [ ] Implement **password reset** functionality
- [ ] Add **rate limiting** to prevent brute force attacks
- [ ] Implement **token blacklisting** for compromised tokens
- [ ] Add **OAuth2** social login (Google, GitHub)
- [ ] Create **Swagger/OpenAPI** documentation
- [ ] Add comprehensive **unit and integration tests**
- [ ] Implement **Redis caching** for token validation
- [ ] Add **Docker Compose** setup for easy deployment
- [ ] Create **CI/CD pipeline** with GitHub Actions

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. **Fork** this repository
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

Please ensure your code follows the existing style and includes appropriate tests.

---

## 📝 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

You are free to use, modify, and distribute this software in your projects.

---

## 👨‍💻 Author

**Ayman Hamad**

- GitHub: [@Aymanhamad2002](https://github.com/Aymanhamad2002)
- Email: [Your Email]

---

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- Auth0 for JWT libraries and documentation
- The open-source community for inspiration

---

## 📞 Support

If you have questions or run into issues:

1. Check the [Issues](https://github.com/Aymanhamad2002/jwt-authentication-app/issues) page
2. Open a new issue if your problem isn't already addressed
3. Provide detailed information about your environment and the problem

---

## ⭐ Show Your Support

If you find this project helpful, please give it a **star** ⭐ on GitHub!

This helps others discover the project and motivates continued development.

---

**Happy Coding! 🚀**
