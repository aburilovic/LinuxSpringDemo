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
    docker tag springbootsandbox:1.0.5 aburilovic/dockersandbox:1.0.5
    
    docker login
    
    docker push aburilovic/dockersandbox:1.0.5
    
    docker run -p 8081:8081 springbootsandbox:1.0.5
    or
    docker run -p 8081:8081 aburilovic/dockersandbox:1.0.5
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

- Add support for Spring Actuator and Springdoc Openapi (Swagger UI) 🗸
      
      Actuator url: /actuator/metrics/{metric-name}
      Swagger UI: /swagger-ui/index.html
      
- Add support for OAuth2 (Keycloak) 🗸
  * run initial keycloak server and mount dev directory to persist keycloak data (make sure directory has valid permissions):
      ```
      docker run -d --name keycloak \ 
      -p 8080:8080 \
      -e KEYCLOAK_ADMIN=admin \
      -e KEYCLOAK_ADMIN_PASSWORD=admin \
      -v $(pwd)/keycloak_data:/opt/keycloak/data \
      quay.io/keycloak/keycloak:25.0.6 start-dev
      ```
  * generate realm and all the clients, roles, users...
  * stop and remove the keycloak container
      ```
      docker stop keycloak
      docker rm keycloak
      ```
  * run export command to get all configuration (including users) in one file (keycloak_export) and make sure directories has valid permissions:
      ```
      docker run --rm \
      -e KEYCLOAK_ADMIN=admin \
      -e KEYCLOAK_ADMIN_PASSWORD=admin \
      -v $(pwd)/keycloak_data:/opt/keycloak/data \
      -v $(pwd)/keycloak_export:/opt/keycloak/data/import \
      quay.io/keycloak/keycloak:25.0.6 \
      export --realm sp-demo --dir /opt/keycloak/data/import --users realm_file
      ```
  * run another keycloak instance and consume exported configuration (simulates using the same config for another dev environment):
      ```
      docker run -d --name keycloak-dev \
      -p 8085:8080 \
      -e KEYCLOAK_ADMIN=admin \
      -e KEYCLOAK_ADMIN_PASSWORD=admin \
      -v $(pwd)/keycloak_export:/opt/keycloak/data/import \
      quay.io/keycloak/keycloak:25.0.6 start-dev --import-realm
      ```
  * to Login with Keycloak, first obtain token using some client like Postman:
      ```
      POST http://192.168.49.2:32080/realms/sp-demo/protocol/openid-connect/token
      Body:
      grant_type:password
      grant_type:password
      client_id:sb-demo-client
      username:sb-demo-user
      password:1111
      client_secret:YAlyoENIUHBMufTZEU8EW1cIKWrU5JsJ
      ```
  * use provided access token with other API requests that are secured
      ```
      For Authorization specify Bearer access_token
      ```
  * use https://jwt.io/ to decode the token and check various fields like 'allowed-origins', 'realm_access', 'scope' etc.

- Add support for Spring Reactive 🗸

- Add support for simple Vue Application that consumes Spring Boot API 🗸