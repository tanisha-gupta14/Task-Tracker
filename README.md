# Task Tracker

A Spring Boot task management backend with JWT authentication, role-based access control, and PostgreSQL persistence.

## Description

Task Tracker lets users register, authenticate, and manage task lists and tasks securely. Regular users can create, update, delete, and retrieve their task lists and tasks, while admins can inspect users, their task lists, and task progress.

## Features

- JWT-based authentication
- User registration and login
- Role-based authorization (`USER`, `ADMIN`)
- CRUD operations for task lists
- CRUD operations for tasks inside a task list
- Admin endpoints for user and task overview
- PostgreSQL persistence with Spring Data JPA
- CORS enabled for `http://localhost:5173`
- H2 available in test scope

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (`jjwt`)
- PostgreSQL

## Prerequisites

- Java 21+
- Maven
- PostgreSQL
- Environment variables:
  - `DB_USERNAME`
  - `DB_PASSWORD`

## Setup

1. Clone the repository
2. Configure PostgreSQL and create a database, e.g. `TaskApplication`
3. Set environment variables for database credentials
4. Update `src/main/resources/application.properties` if needed

Example `application.properties`:

```properties
spring.application.name=tasks

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/TaskApplication
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
