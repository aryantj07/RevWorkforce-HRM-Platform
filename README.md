# RevWorkforce HRM Platform

A cloud-native Human Resource Management (HRM) platform built using **Java, Spring Boot, Spring Cloud, MySQL, Docker, and Kubernetes**.

## Overview

RevWorkforce provides employee management, user authentication, leave management, performance management, reporting, and notifications through a **microservices architecture**.

## Architecture

The platform consists of independent microservices supported by:

- API Gateway
- Eureka Service Discovery
- Spring Cloud Config Server
- MySQL
- Docker & Kubernetes
- Jenkins CI/CD

## System Architecture

```mermaid
flowchart TB

    U["Employees / Managers / Admins"]
    F["Frontend<br/>Spring Boot MVC<br/>Port 8090"]
    G["API Gateway<br/>Spring Cloud Gateway<br/>Port 8080"]

    U --> F
    F --> G

    subgraph SERVICES["Business Microservices"]
        US["User Service<br/>Port 8081"]
        LS["Leave Service<br/>Port 8082"]
        PS["Performance Service<br/>Port 8083"]
        EMS["Employee Management Service<br/>Port 8084"]
        NS["Notification Service<br/>Port 8085"]
        RS["Reporting Service<br/>Port 8086"]
    end

    G --> US
    G --> LS
    G --> PS
    G --> EMS
    G --> NS
    G --> RS

    LS --> NS
    PS --> NS
    EMS --> NS

    subgraph INFRA["Infrastructure"]
        CS["Config Server<br/>Port 8888"]
        ES["Eureka Server<br/>Port 8761"]
    end

    CS -.-> US
    CS -.-> LS
    CS -.-> PS
    CS -.-> EMS
    CS -.-> NS
    CS -.-> RS
    CS -.-> G

    US -.-> ES
    LS -.-> ES
    PS -.-> ES
    EMS -.-> ES
    NS -.-> ES
    RS -.-> ES
    G -.-> ES

    DB[("MySQL")]

    US --> DB
    LS --> DB
    PS --> DB
    EMS --> DB
    NS --> DB
    RS --> DB

    subgraph DEVOPS["DevOps"]
        JENKINS["Jenkins CI/CD"]
        DOCKER["Docker / Docker Compose"]
        K8S["Kubernetes"]
    end

    JENKINS --> DOCKER
    DOCKER --> K8S
    K8S -.-> G

```

### Architecture Overview

RevWorkforce follows a cloud-native microservices architecture. The frontend communicates with the backend through the API Gateway, which routes requests to six independent business microservices.

- **User Service** – authentication, authorization and user profiles
- **Leave Service** – leave applications, balances, approvals and holidays
- **Performance Service** – reviews, goals and manager feedback
- **Employee Management Service** – employees, departments, designations and announcements
- **Notification Service** – employee and manager notifications
- **Reporting Service** – HR dashboards and organizational reports

**Eureka Server** provides service discovery, while **Config Server** centralizes configuration. Each business service maintains its own data boundaries. Docker, Kubernetes and Jenkins provide the containerization, orchestration and CI/CD layers.

## Microservices

- User Service
- Leave Service
- Performance Service
- Employee Management Service
- Reporting Service
- Notification Service

## Tech Stack

**Java 17 | Spring Boot | Spring Cloud | Spring Data JPA | MySQL | Maven | Docker | Kubernetes | Jenkins | Git**

## Team

Developed as a team project using GitHub feature branches, pull requests, code reviews, and controlled merges into the `main` branch.

## Team Contribution

- Yash — Employee Management Service
- Aryan — User Service, Reporting Service, Infrastructure and Integration
- Branson — Leave Service and Notification Service
- Ivan — Performance Service
<!-- Auto CI/CD Webhook Triggered -->
