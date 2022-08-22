package com.mustafa.Consumer.controller;

import com.mustafa.Consumer.service.HeadersConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/")
public class HeadersConsumerController {

    HeadersConsumerService consumerService;

    @Autowired
    public HeadersConsumerController(HeadersConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("headersReceive")
    public void headersReceive() throws IOException, TimeoutException {
        consumerService.receive();
    }

}
