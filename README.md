# Final_Thesis_Work
### Version of message API: 1.0

## Details

This is the final work of Globant's Java Academy, consist in a RestAPI for messaging, developed with the next features and requirements:

(for lack of time, in this version the attachments feature is not available)

![image](https://user-images.githubusercontent.com/97646432/160457496-0d7a1af7-4aaf-44cb-805a-757f675b95b3.png)

## Deploy with Docker

The project has 2 Docker files, the first (_in ./db folder_) that defines the database environment with its parameters, scripts and config, and the second (_in root directory_) to create a docker container for the java application, both orchestrated by a docker-compose.yml file.

## Architecture

- ### Persistence:
  For persistence uses a Microsoft SQL Server relational database.
- ### Backend:
  All the backend was developed using Spring Boot framework, with web, jpa/hibernate, json web token (jwt) and security dependencies to manage the data, authentication and login/sign-up.

## Documentation

Once deployed the project it includes a Swagger documentation for RestAPI endpoints and schemas, which does not require authentication, can be found at the URL: http://root_domain/swagger-ui.html
