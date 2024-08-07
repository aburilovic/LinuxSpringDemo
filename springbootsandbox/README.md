# Spring Boot Sandbox
Simple Spring Boot project on Linux machine to test various technologies.

## The goal
The goal of this project is to create 'hello world' environment and cover these topics:

- Create Initial Spring Boot project using Maven 🗸
- Add simple Rest endpoint 🗸
- Create docker image 🗸  
  `./mvnw spring-boot:build-image`


- Use Docker Hub to deploy new image 🗸

    ```
    docker tag springbootsandbox:0.0.1-SNAPSHOT aburilovic/dockersandbox:0.0.1-SNAPSHOT
    
    docker login
    
    docker push aburilovic/dockersandbox:0.0.1-SNAPSHOT
    
    docker run -p 8081:8081 springbootsandbox:0.0.1-SNAPSHOT
    or
    docker run -p 8081:8081 aburilovic/dockersandbox:0.0.1-SNAPSHOT
    ```

- Use existing MySQL image and connect it with previously created image
- Use Kafka as event streaming platform

    ```
    docker pull apache/kafka:3.7.0
  
    docker run -p 9092:9092 apache/kafka:3.7.0
    ```

- Kubernetes (TBD)
