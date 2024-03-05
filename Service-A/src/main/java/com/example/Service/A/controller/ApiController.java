package com.example.Service.A.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Service.A.service.SagaServiceA;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class ApiController {
    private final SagaServiceA sagaServiceA;

    public ApiController(SagaServiceA sagaServiceA) {
        this.sagaServiceA = sagaServiceA;
    }

    @GetMapping("/invoke-apis-in-saga")
    public String invokeApisInSaga() {
        sagaServiceA.invokeApisInSaga();
        return "Saga initiated";
    }
    
}
