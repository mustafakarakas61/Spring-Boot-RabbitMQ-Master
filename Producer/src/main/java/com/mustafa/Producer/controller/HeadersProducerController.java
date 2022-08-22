package com.mustafa.Producer.controller;

import com.mustafa.Producer.service.HeadersProducerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/")
public class HeadersProducerController {

    HeadersProducerService headersExchangeService;

    @Autowired
    public HeadersProducerController(HeadersProducerService headersExchangeService) {
        this.headersExchangeService = headersExchangeService;
    }

    @PostMapping("headersCreateQueueAndExchange")
    public void createQueueAndExchange() throws IOException, TimeoutException {
        headersExchangeService.createExchangeAndQueue();
    }

    @PostMapping("headersProducer")
    public void producer() throws IOException, TimeoutException {
        headersExchangeService.publish();
    }

    @PostMapping("createQueueForHeaders")
    @ApiOperation("allOrAny için eğer true seçilirse all, false seçilirse any olacaktır.")
    public void createQueue(@RequestParam String queueName, @RequestParam String existExchangeName, @RequestParam String propS1, @RequestParam String propO1, @RequestParam String propS2, @RequestParam String propO2, @RequestParam()  boolean allOrAny) throws IOException, TimeoutException {
        headersExchangeService.createQueue(queueName,existExchangeName,propS1,propS2, propO1,propO2,allOrAny);
    }

    @PostMapping("sendMessageForHeaders")
    public void sendMessage(@RequestParam String message, @RequestParam String existExchangeName, @RequestParam String propS1,  @RequestParam String propO1,@RequestParam(required = false) String propS2,@RequestParam(required = false) String propO2) throws IOException, TimeoutException {
        headersExchangeService.sendMessage(message,existExchangeName,propS1,propS2,propO1,propO2);
    }
}
