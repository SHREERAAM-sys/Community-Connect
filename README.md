# Community Connect Platform

## Overview
Community Connect is a platform designed to enhance community engagement by facilitating activity management and secure user interactions through a centralized system. The platform aims to foster stronger community bonds and increase participation in local activities.

## Key Features

- **Activity Management**: Create, update, delete, and manage community activities.
- **Secure User Interaction**: Secure signup, login, and profile management.
- **Categorization**: Activities are categorized for easy browsing (e.g., Social, Clean-Up, Fundraiser).
- **Search and Filter**: Search activities by various criteria and filter results for relevance.
- **Notifications**: Implementing microservices with Kafka queues for notifications.
- **Multiple Roles**: Support for activity organizers and regular users.

## Status
This project is completed and operational.

## Technologies Used
- **Spring Boot**
- **Hibernate**
- **Kafka**
- **Thymeleaf**
- **MySQL**
- **Spring Security**
- **Spring Cloud**

## Installation Instructions
To set up the Community Connect platform locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/community-connect.git
   ```
2. Navigate into the project directory:
   ```bash
   cd community-connect
   ```
3. Build the project:
   ```bash
   mvnw clean install
   ```
4. Run the application:
   ```bash
   mvnw spring-boot:run
   ```
5. Access the application at
   ```bash
   http://localhost:8080
   ```
   
