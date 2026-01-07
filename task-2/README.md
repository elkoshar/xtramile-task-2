# Patient Management System

A full-stack web application for managing patient records built with Spring Boot (backend) and Angular (frontend).

## Features

- **CRUD Operations**: Create, Read, Update, Delete patient records
- **Search & Pagination**: Search patients by name or ID with pagination
- **Responsive UI**: Bootstrap-styled Angular frontend
- **RESTful API**: Well-structured REST endpoints
- **In-Memory Database**: H2 database for development

## Prerequisites

Make sure you have the following installed:

- **Java 17 or higher**
- **Maven 3.6+**
- **Node.js 18+ and npm**
- **Angular CLI**

## Getting Started

### 1. Clone the Repository

```bash
git clone git@github.com:elkoshar/xtramile-task-2.git
cd xtramile-task-2
```

### 2. Backend Setup & Running

#### Install Dependencies
```bash
cd task-2/backend
mvn clean install
```

#### Run the Application
```bash
mvn spring-boot:run
```

The backend will start on **http://localhost:8080**

### 3. Frontend Setup & Running

#### Navigate to Frontend Directory
```bash
cd task-2/frontend
```

#### Install Dependencies
```bash
npm install
```

#### Start Development Server
```bash
npm start
# or
ng serve
```

The frontend will start on **http://localhost:4200**
