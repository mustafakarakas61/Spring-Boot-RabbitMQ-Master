package com.mustafa.Consumer.controller;

import com.mustafa.Consumer.service.DirectConsumerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/")
public class DirectConsumerController {

    DirectConsumerService consumerService;

    @Autowired
    public DirectConsumerController(DirectConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    /*
    @GetMapping("receiver")
    @ApiOperation("Start the consumers(1-2-3)")
    public void receiver() throws IOException, TimeoutException {
        consumerService.receive();
    }
    */
    @GetMapping("directReceiver")
    @ApiOperation("Mesajı almak istediğiniz kuyruğun ismini girmelisiniz.")
    public String receiver(String queueName) throws IOException, TimeoutException {

        return consumerService.startConsumerForThisQueue(queueName);
    }


}
