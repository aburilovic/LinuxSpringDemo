# Spring Boot Sandbox
Simple Spring Boot project on Linux machine to test various technologies.

## The goal
The goal of this project is to create 'hello world' environment and cover these topics:

- Create Initial Spring Boot project using Maven 🗸
- Add support for JPA with simple CRUD operations, use in-memory database like H2 🗸
- Add simple Rest endpoints 🗸
- Add unit and integration tests 🗸
- Create docker image 🗸
  `./mvnw spring-boot:build-image`

- Docker Hub to deploy new image 🗸

    ```
    docker tag springbootsandbox:0.0.2 aburilovic/dockersandbox:0.0.2
    
    docker login
    
    docker push aburilovic/dockersandbox:0.0.2
    
    docker run -p 8081:8081 springbootsandbox:0.0.2
    or
    docker run -p 8081:8081 aburilovic/dockersandbox:0.0.2
    ```
- Kafka as event streaming platform 🗸

    ```
    docker pull bitnami/kafka:3.0.2
  
    docker run -p 9092:9092 bitnami/kafka:3.0.2
    ```

- Docker compose to run Kafka, Zookeeper and Spring Boot app (run from 'docker' folder) 🗸

    ```
    docker compose up
    docker compose down
    ```

- Kubernetes to run previously created images 🗸
  * to generate .yaml configuration use:

      ```
      kubectl create deployment sb-demo-deployment --image=aburilovic/dockersandbox:0.0.1-SNAPSHOT --dry-run=client -o=yaml > spring-boot-demo.yaml
      kubectl create service nodeport service-sb-demo --tcp=8081:8081 --dry-run=client -o yaml > service-sb-demo.yaml
      ```
    or just write manually configuration to desired files (workloads.yaml, services.yaml etc.). Check 'kubernetes' folder.
  
  * apply those changes:
      ```
      kubectl apply -f sb-demo-workloads.yaml
      kubectl apply -f sb-demo-services.yaml
      ```
  * use 'describe' command to check more details about specified pods, services and any other kubernetes working unit:
      ```
      kubectl describe kubectl logs pod/sb-demo-679b844f7b-nj2hr
      ```
  * logs can be checked with kubernetes 'logs' command:
      ```
      kubectl logs kubectl logs pod/sb-demo-679b844f7b-nj2hr
      ```
    'tail' option to check last N log entries, 'grep' to pipeline result for filtering.
  
- Use existing MySQL image and connect it with previously created image

- Add support for Spring Actuator and Springdoc Openapi 🗸

