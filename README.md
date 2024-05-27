# StarryTasks

StarryTasks is a task and reward management application for children, enabling parents and children to collaborate and monitor progress.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [ERD](#erd)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Credits](#credits)

## Features

- User registration and login
- Task and reward management for children
- Progress monitoring and statistics
- User roles: Parent and Child
- Household invitation system

## Technologies

- **Frontend**: React
- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **AMQP**: RabbitMQ
- **Containerization**: Docker


## ERD
![Untitled (5)](https://github.com/juliakam588/StarryTasks/assets/77432872/43ffe009-cf96-4b45-97f3-c149f741bbb9)


## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/juliakam588/StarryTasks.git
    cd StarryTasks
    ```

2. Backend:
    ```bash
    cd backend
    mvn clean install
    ```

3. Frontend:
    ```bash
    cd frontend
    npm install
    ```

## Configuration

1. Backend:
   - Configure the database in `application.properties` or `application.yml`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5433/starrytasks
     spring.datasource.username=YourEmail
     spring.datasource.password=YourPassword
     spring.jpa.hibernate.ddl-auto=update
     application.security.jwt.secret-key=YourSecretKey
     application.security.jwt.expiration=86400000
     ```

2. Frontend:
   - Configure the backend URL in `axiosConfig.js`:
     ```javascript
     const axiosInstance = axios.create({
         baseURL: 'http://localhost:8080',
         withCredentials: true
     });
     ```

## Running the Application

1. Backend:
    ```bash
    cd backend
    mvn spring-boot:run
    ```

2. Frontend:
    ```bash
    cd frontend
    npm run dev
    ```

## Usage

1. Register a user on the `/register` page.
   ![image](https://github.com/juliakam588/StarryTasks/assets/77432872/0ccd681c-8e53-4ce9-94d5-e92eee433025)

2. Log in a user on the `/login` page.
 ![image](https://github.com/juliakam588/StarryTasks/assets/77432872/d2be9cc9-e796-47a7-a8ed-b544ce5e0d26)

3. After logging in, if the user has no assigned role, they will select a role on the `/role` page.
   ![image](https://github.com/juliakam588/StarryTasks/assets/77432872/4386b653-e23d-4abe-9c52-f311bd376371)

4. Parents can add tasks and rewards, and children can view and complete them.
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/8b2e7233-7beb-48de-aca5-522298ad2e92)
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/17b9ffd5-8016-4630-af9e-7ba26448e59a)
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/e0b13846-18db-4732-9e58-3d81f12d5e12)
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/41145f41-4ad4-41da-91b2-d7aa2002ed03)
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/a0a89509-a307-4fb2-914b-fefa418c5d77)

   
5. Parents can monitor the child's progress on the stats page.
![image](https://github.com/juliakam588/StarryTasks/assets/77432872/7dd14cc6-b519-4052-8976-f935004620ca)



## Credits

This project was developed and designed by [Julia Kamuda](https://github.com/juliakam588). 

For any questions or further information, please feel free to contact me:

- Email: julka.kamuda@gmail.com
