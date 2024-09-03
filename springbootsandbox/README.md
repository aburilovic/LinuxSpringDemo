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
- Use Kafka as event streaming platform 🗸

    ```
    docker pull bitnami/kafka:3.0.2
  
    docker run -p 9092:9092 bitnami/kafka:3.0.2
    ```

- Use Docker compose to run kafka, zookeeper and spring boot app (run from 'docker' folder) 🗸

    ```
    docker compose up
    docker compose down
    ```

- Kubernetes
    * use previous docker image and run it locally on minikube with one Kubernetes deployment


- Use existing MySQL image and connect it with previously created image