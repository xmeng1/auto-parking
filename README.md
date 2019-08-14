# Auto Parking System Doc

## Introduction

## Heroku Instance

I have deployed the application to the Heroku (free Dyno):

Webapp: https://auto-parking.herokuapp.com/

The Swagger-UI: https://auto-parking.herokuapp.com/swagger-ui.html

So it can be quick checked and reviewed including the detail of API doc.

## API 

The API can be accessed by using Swagger-UI: 

http://localhost:8080/swagger-ui.html

(online version: https://auto-parking.herokuapp.com/swagger-ui.html)

![Swagger-UI-screenshot](/doc/img/api-swagger-ui.png)
 
## The Basic Frontend App

I use Angular and Material Design implement a basic frontend web app 

## How to build and run

The application is based on the Spring Boot and Angular. Meanwhile, I have use the `frontend-maven-plugin` build and copy frontend resource to the target, so run the application in local is very easy with the following command.

The default development port is **8080**, please make sure the port is not listened by other application. After run the command, visit the browser : http://localhost:8080

### Linux/Mac OS

```bash
./mvnw spring-boot:run
```

### Windows

```bash
./mvnw.cmd spring-boot:run
```

### Separate Backend and Frontend

If you would like to run the backend and frontend separately, just need to run the frontend with default port `9000`.

```bash
cd src/main/webapp/
npm start
```

The Angular development configuration use the default backend url and port: http://localhost:8080
 
### Spring Boot Rest API and Swagger

The backend is based on the Spring Boot with Rest API and Swagger support with the SpringFox

### Angular Frontend Framework

The frontend is based on the Angular with Material Design and several 3rd packages such as `ngx-logger` and `ISO-639-1` and so on.
