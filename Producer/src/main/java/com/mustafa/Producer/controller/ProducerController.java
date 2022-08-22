package com.mustafa.Producer.controller;

import com.mustafa.Producer.service.ProducerService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/")
public class ProducerController {

    ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }
/*
    @PostMapping("createExchangeAndQueue")
    @ApiOperation("direct-exchange ve Queue(1-2-3)'leri yayına geçirmek için")
    public void createExchangeAndQueue() throws IOException, TimeoutException {
        producerService.createExchangeAndQueue();
    }
*/
    /*
    @PostMapping("producer")
    @ApiOperation("Start the producers(1-2-3) and send the messages(1-2-3)[bind queue(1-2-3) to directExchange with routing_keys(1-2-3)]")
    public void producer() throws IOException, TimeoutException {
        producerService.publish();
    }
*/
    /*
    @PostMapping("producer2")
    @ApiOperation("Bir mesaj yazın, queue'yi ve Routing_Key'inizi belirtin. İsteği gönderdiğinizde yayına geçecektir.")
    public void producer2(@RequestParam String message, @RequestParam String routing_key, @RequestParam String queue) throws IOException, TimeoutException {
        producerService.publish(message, routing_key);
        producerService.createExchangeAndQueueUser(queue, routing_key);
    }
    */


    @PostMapping("sendMessageForDirect")
    public String sendMessage(@RequestParam String newMessage, @RequestParam String newRoutingKeyName, @RequestParam() String existExchangeName) throws IOException, TimeoutException {

        producerService.sendMessage(newMessage, newRoutingKeyName, existExchangeName);

        return "Mesajınız Kuyruğa Eklendi !";
    }

    @PostMapping("sendMessageForFanout")
    public String sendMessage(@RequestParam String newMessage, @RequestParam() String existExchangeName) throws IOException, TimeoutException {

        producerService.sendMessage(newMessage, existExchangeName);

        return "Mesajınız Kuyruğa Eklendi !";
    }


    @PostMapping("createQueue")
    @ApiOperation("Bir kuyruk oluşturmak için")
    public void createQueue(@RequestParam String queueName) throws IOException, TimeoutException {
        producerService.createQueue(queueName);
    }

    @PostMapping("createQueueWithBind")
    public void createQueue(@RequestParam String queueName,@RequestParam String existExchangeName,@RequestParam String routingKeyName) throws IOException, TimeoutException {
        producerService.createQueue(queueName, existExchangeName, routingKeyName);
    }

    @PostMapping("createQueueWithOnlyExchange")
    public void createQueue(@RequestParam String queueName,@RequestParam String existExchangeName) throws IOException, TimeoutException {
        producerService.createQueue(queueName, existExchangeName);
    }



    @PostMapping("createExchange")
    @ApiOperation("Bir exchange tipi oluşturmak için: DirectExchange=D or d, FanoutExchange=F or f, HeaderChange=H or h, TopicExchange=T or t")
    public void createExchange(@RequestParam String exchangeName,@RequestParam char c) throws IOException, TimeoutException {

        if (c == 'D' || c == 'd') {
            producerService.createExchangeDirect(exchangeName);
        }
        if (c == 'F' || c == 'f') {
            producerService.createExchangeFanout(exchangeName);
        }
        if (c == 'H' || c == 'h') {
            producerService.createExchangeHeader(exchangeName);
        }
        if (c == 'T' || c == 't') {
            producerService.createExchangeTopic(exchangeName);
        }
    }


}
