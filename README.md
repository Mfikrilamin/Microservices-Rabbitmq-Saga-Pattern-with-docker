# Microservices - Saga Pattern with RabbitMQ and Docker

This repository provides a tutorial on implementing Microservices using the Saga Pattern with RabbitMQ and Docker.

## How to Run the Program

1. **Clone the Repository:**
`git clone <repository_url>`

2. **Build the Project:**
`mvn clean package`

3. **Start Docker Engine and Run Docker Compose:**
`docker-compose up -d`

4. **Access RabbitMQ Management Interface:**
access the RabbitMQ management interface at [http://localhost:15672](http://localhost:15672)  
Use credentials `username: guest` & `password: guest`
![Expected RabbitMQInterface](https://github.com/Mfikrilamin/Microservices-Rabbitmq-Saga-Pattern-with-docker/blob/main/images/RabbitMQ%20Interface.png)

6. **Verify Connections:**
Ensure that there are two connections established in the RabbitMQ management interface.
![Expected Connection](https://github.com/Mfikrilamin/Microservices-Rabbitmq-Saga-Pattern-with-docker/blob/main/images/Service%20Connection.png)

7. **Hit the API:**
Make `GET` request to `localhost:8095/api/invoke-apis-in-saga`:  
[http://localhost:8095/api/invoke-apis-in-saga](http://localhost:8095/api/invoke-apis-in-saga)

8. **Read Logs in Docker:**
Check the Docker logs to verify the execution. The expected result should be similar to the image below.
![Expected Logs](https://github.com/Mfikrilamin/Microservices-Rabbitmq-Saga-Pattern-with-docker/blob/main/images/Docker%20log.png)
