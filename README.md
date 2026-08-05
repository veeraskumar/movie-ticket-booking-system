# 🎬 Movie Ticket Booking System (MTBS)

A full-stack Movie Ticket Booking System built with **Spring Boot**, **React**, and **PostgreSQL**. The application supports movie ticket booking, theater management, JWT authentication, role-based authorization, and an owner dashboard for managing theaters and shows.

## ✨ Features

### 👤 Authentication

- User registration and login
- JWT-based authentication
- Password encryption with BCrypt
- Forgot password via email
- Reset password using secure token

### 🎟️ Customer Features

- Browse available theaters
- View movie shows
- Book movie tickets
- Cancel booked tickets
- View booking history
- Manage profile

### 🏢 Theater Owner Features

- Create, update, and delete theaters
- Create, update, and cancel movie shows
- Manage multiple screens
- View all shows for owned theaters

### 🔐 Authorization

- Role-based access control
- `ROLE_USER`
- `ROLE_OWNER`
- `ROLE_ADMIN`

Users can only access their own resources, while theater owners can manage only their own theaters and shows.

---

# 🛠️ Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- JWT Authentication
- Lombok
- Jakarta Validation
- Spring Mail
- Maven

## Frontend

- React
- TypeScript
- Vite
- React Router
- React Hook Form
- Zod
- Axios
- Tailwind CSS
- shadcn/ui

---

# 📁 Project Structure

```text
movie-ticket-booking-system/
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── security/
│   ├── exception/
│   └── config/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── api/
│   │   ├── types/
│   │   └── utils/
│
└── README.md
```

---

# 🗄️ Database

Main entities:

- User
- Theater
- Show
- Booking
- PasswordResetToken

Relationships:

```
User
 ├── owns many Theaters
 └── books many Tickets

Theater
 └── has many Shows

Show
 └── has many Bookings
```

---

# 🔑 Authentication Flow

1. User registers
2. Password is encrypted using BCrypt
3. User logs in
4. Spring Security authenticates credentials
5. JWT token is generated
6. Client stores JWT
7. Every protected request includes:

```
Authorization: Bearer <token>
```

---

# 🚀 Running Locally

## Backend

```bash
cd backend

./mvnw spring-boot:run
```

## Frontend

```bash
cd frontend

npm install
npm run dev
```

---

# ⚙️ Environment Variables

Backend `.env`

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=
JWT_EXPIRATION=

MAIL_USERNAME=
MAIL_PASSWORD=
```

---

# 🌐 API Documentation

Swagger UI

```
/swagger-ui/index.html
```

OpenAPI Docs

```
/v3/api-docs
```

---

# 📸 Screens

- Home
- Login
- Register
- Theater Dashboard
- Show Dashboard
- Booking Page
- Profile
- Owner Dashboard

(Add screenshots here)

---

# 🔒 Security

- JWT Authentication
- BCrypt Password Encoding
- Role-based Authorization
- Method-level Security (`@PreAuthorize`)
- Resource ownership validation
- Input validation using Jakarta Validation and Zod

---

# 🚀 Deployment

Backend

- Railway

Frontend

- Vercel

Database

- PostgreSQL

---

# 📚 What I Learned

- Spring Boot Architecture
- Spring Security
- JWT Authentication
- Role-Based Authorization
- REST API Design
- Hibernate & JPA
- React with TypeScript
- React Hook Form
- Zod Validation
- Axios API Integration
- Tailwind CSS
- shadcn/ui
- PostgreSQL
- Full-stack application deployment

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a Pull Request

---

# 📄 License

This project is licensed under the MIT License.
