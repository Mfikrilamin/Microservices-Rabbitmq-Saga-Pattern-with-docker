package com.example.Service.B.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Service.B.model.SagaMessage;

@Service
public class SagaServiceB {
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    public SagaServiceB(RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "api1-consumer-queue")
    public void handleApi1(SagaMessage message) {
        try {
            System.out.println("successfully consume message in api1-consumer-queue");

            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key",
                    new SagaMessage("successfully consume API1 message in api1-consumer-queue",
                            UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key", new SagaMessage("fail"));
            return;
        }
    }

    @RabbitListener(queues = "api2-consumer-queue")
    public void handleApi2(SagaMessage message) {
        Long orderId = message.getOrderId();
        try {
            Thread.sleep(5000);
            System.out.println("successfully consume message in api2-consumer-queue");

            rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
                    new SagaMessage("successfully consume API2 message in api2-consumer-queue", orderId));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
                    new SagaMessage("fail", orderId));
            return;
        }
    }

    @RabbitListener(queues = "api3-consumer-queue")
    public void handleApi3(SagaMessage message) {
        Long orderId = message.getOrderId();
        try {
            Thread.sleep(3000);
            System.out.println("successfully consume API3 message in api3-consumer-queue");
            
            rabbitTemplate.convertAndSend("saga-exchange", "api3-producer-routing-key",
                    new SagaMessage("successfully consume API3 in api3-consumer-queue"));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api3-producer-routing-key",
                    new SagaMessage("fail", orderId));
            return;
        }
    }

    @RabbitListener(queues = "compensate-api1-queue")
    public void handleCompensateApi1Request(SagaMessage message) {
        try {
            System.out.println("message failed in compensate-api1-queue");
        } catch (Exception e) {
            System.out.println("API1 Compensation Failed");
        }
    }

    @RabbitListener(queues = "compensate-api2-queue")
    public void handleCompensateApi2Request(SagaMessage message) {
        try {
            System.out.println("message failed in compensate-api1-queue");
        } catch (Exception e) {
            System.out.println("API2 Compensation Failed");
        }
        handleCompensateApi1Request(message);
    }
}
