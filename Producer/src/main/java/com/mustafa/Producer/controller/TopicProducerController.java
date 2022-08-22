package com.mustafa.Producer.controller;

import com.mustafa.Producer.service.TopicProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/")
public class TopicProducerController {

    TopicProducerService producerService;

    @Autowired
    public TopicProducerController(TopicProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("createExchangeAndQueue")
    public void createExchangeAndQueue() throws IOException, TimeoutException {
        producerService.createExchangeAndQueue();
    }

    @PostMapping("topicProducer")
    public void producer() throws IOException, TimeoutException {
        producerService.publish();
    }

    @PostMapping("createTopicQueue")
    public void createQueue(@RequestParam String queueName, @RequestParam String existExchangeName, @RequestParam String routingPatternName) throws IOException, TimeoutException {
        producerService.createQueue(queueName,existExchangeName,routingPatternName);

    }

    @PostMapping("sendTopicMessage")
    public void sendTopicMessage(@RequestParam String newMessage, @RequestParam String newRoutingKeyName, @RequestParam String existExchangeName) throws IOException, TimeoutException {
        producerService.sendMessage(newMessage,newRoutingKeyName,existExchangeName);
    }
}
