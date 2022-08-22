package com.mustafa.Consumer;

import com.mustafa.Consumer.service.DirectConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class ConsumerApplication {


	public static void main(String[] args)  {
		SpringApplication.run(ConsumerApplication.class, args);

	}

}
