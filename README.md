# 🚀 Spring Boot CRUD Web Application

A web application built using Spring Boot that implements core CRUD (Create, Read, Update, Delete) functionalities.  
This project focuses on backend development fundamentals and containerization using Docker.

---

## 📌 Features

- Create, Read, Update, Delete operations
- RESTful API design
- Database integration
- Backend logic implementation
- Docker containerization

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- SQL Database (MySQL / PostgreSQL / H2)
- Docker

---

## ⚙️ How It Works

- Users can perform CRUD operations through API endpoints or UI
- Data is stored and managed in a relational database
- Application is containerized using Docker for easy deployment

---

## 🐳 Docker Setup

### Build Docker Image
```bash
docker build -t springboot-crud-app .
## 📡 API Endpoints

| Method | Endpoint        | Description        |
|--------|---------------|--------------------|
| GET    | /api/items     | Get all items      |
| GET    | /api/items/{id} | Get item by ID     |
| POST   | /api/items     | Create new item    |
| PUT    | /api/items/{id} | Update item        |
| DELETE | /api/items/{id} | Delete item        |
