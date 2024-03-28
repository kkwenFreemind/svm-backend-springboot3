# Spring Boot 3 JWT Authentication Project

username/password kevin/1234567
## Introduction
- it is spring boot backend project
- it is a basic security template project

## Program

- using spring boot 3 + security + jwt 
- using StopWatch for monitor api performance
- using JPA
- 

## Function
- SecurityFilterChain 白名單url (application.yml url-config)
- api event records (ipAddress,UserAgent,Method... )
- Global cors disabled


## ER Model
user account / role / menu / api privilege relation 
![ER](img/testdb.png)

## User Registration, User Login and Authorization process
![spring-boot-jwt-authentication-spring-security-flow](img/spring-boot-jwt-authentication-spring-security-flow.png)

## Spring Boot Server Architecture with Spring Security
![spring-boot-jwt-authentication-spring-security-architecture](img/spring-boot-jwt-authentication-spring-security-architecture.png)