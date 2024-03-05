package com.example.Service.A.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.Service.A.model.SagaMessage;

@Service
public class SagaServiceA {
    private final RabbitTemplate rabbitTemplate;

    public SagaServiceA(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void invokeApisInSaga() {
        rabbitTemplate.convertAndSend("saga-exchange", "api1-consumer-routing-key", new SagaMessage("api1"));
        System.out.println("SENDING API1...");
    }

    @RabbitListener(queues = "api1-producer-queue")
    public void handleApi1Response(SagaMessage message) {
        System.out.println("Now in api1-producer-queue : " + message.getStatus());

        if (message.getStatus().equals("successfully consume API1 message in api1-consumer-queue")) {
            System.out.println("API1 SUCCESSFULLY CONSUMED. SENDING API2...");
            rabbitTemplate.convertAndSend("saga-exchange", "api2-consumer-routing-key",
                    new SagaMessage("API2", message.getOrderId()));
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API1 execution failed");
        }
    }

    @RabbitListener(queues = "api2-producer-queue")
    public void handleApi2Response(SagaMessage message) {
        System.out.println("Now in api2-producer-queue : " + message.getStatus());

        if (message.getStatus().equals("successfully consume API2 message in api2-consumer-queue")) {
            System.out.println("API2 SUCCESSFULLY CONSUMED. SENDING API3...");
            rabbitTemplate.convertAndSend("saga-exchange", "api3-consumer-routing-key",
                    new SagaMessage("API3", message.getOrderId()));
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API2 execution failed compensation initiated");
            rabbitTemplate.convertAndSend("saga-exchange", "compensate-api1-routing-key",
                    new SagaMessage("Fail", message.getOrderId()));
        }
    }

    @RabbitListener(queues = "api3-producer-queue")
    public void handleApi3Response(SagaMessage message) {
        System.out.println("Now in api3-producer-queue : " + message.getStatus());

        if (message.getStatus().equals("successfully consume API3 in api3-consumer-queue")) {
            System.out.println("ALL API SUCCESSFULLY EXECUTED");
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API3 execution failed compensation initiated");
            rabbitTemplate.convertAndSend("saga-exchange", "compensate-api2-routing-key",
                    new SagaMessage("Fail", message.getOrderId()));
        }
    }
}
