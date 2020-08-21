# Referring Domin Tracker MicroService
## Referring domain defination:- 
- Referring domains are websites from which the target website or web page has one or more backlinks. 
## Technical stack
- REST API
- Maven
- Spring boot
- Spring Data
- Tomcat
- H2 Database
- Postman
- Swagger
--------
## Important hooks :-
- Maven - [pom.xml](pom.xml)
- Application properties - [application.yml](src/main/resources/application.yml)
- Runnable Spring Boot Application - [RefferingDomainTracker](src/main/java/com/nitin/java/referring/domain/tracker/Application.java)
- REST endpoints - [RequestMappings.java](src/main/java/com/nitin/java/referring/domain/tracker/controller/requestMappings/RequestMappings.java)
--------
## Swagger

- **Api-doc** http://localhost:8080/v2/api-docs
- **UI** http://localhost:8080/swagger-ui.html#/

--------
## REST Contracts

#### 1. Register Domain
- **Request URI:-** /registerDomain/{domainName}
- **REST method:-** GET
- **Response status:-** 
    - 200

#### 2. Track Domain
- **Request URI:-** /trackDomain/{domainName}/**
- **REST method:-** GET
- **Response status:-** 
    - 200 (When able to find and track domain)
    - 400 (WHen domain name is not registered or deactivated

#### 3. Rank Domain (Default size)
- **Request URI:-** /rankDomain
- **REST method:-** GET
- **Response status:-** 
    - 200
- **Response Body Example:-** 
```
[
    {
        "id": 9,
        "domainName": "www.irs.com",
        "hitCount": 342,
        "isActive": true
    },
    {
        "id": 10,
        "domainName": "www.google.com",
        "hitCount": 223,
        "isActive": true
    },
    {
        "id": 15,
        "domainName": "www.robinhood.com",
        "hitCount": 48,
        "isActive": true
    }
] 
```
    
#### 4. Rank Domain (Custom size)
- **Request URI:-** /rankDomain/{size}
- **REST method:-** GET
- **Response status:-** 
    - 200    
- **Response Body Example:-**
```
[
    {
        "id": 9,
        "domainName": "www.irs.com",
        "hitCount": 342,
        "isActive": true
    },
    {
        "id": 10,
        "domainName": "www.google.com",
        "hitCount": 223,
        "isActive": true
    }
] 
```
  
