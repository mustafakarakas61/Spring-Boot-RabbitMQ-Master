package com.mustafa.Consumer.controller;

import com.mustafa.Consumer.service.TopicConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/")
public class TopicConsumerController {

    TopicConsumerService consumerService;

    @Autowired
    public TopicConsumerController(TopicConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping("topicReceiver")
    public void receiver() throws IOException, TimeoutException {
        consumerService.receive();
    }
}
