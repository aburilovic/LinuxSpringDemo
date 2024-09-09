# Spring Boot Sandbox
Simple Spring Boot project on Linux machine to test various technologies.

## The goal
The goal of this project is to create 'hello world' environment and cover these topics:

- Create Initial Spring Boot project using Maven 🗸
- Add simple Rest endpoint 🗸
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
  * logs can be check
  
- Use existing MySQL image and connect it with previously created image