package com.calpullix.service.client.classification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableCircuitBreaker
@EnableAutoConfiguration( exclude = RabbitAutoConfiguration.class) 
@ComponentScan(basePackages = "com.calpullix")
@EnableDiscoveryClient
public class CalpullixServiceClientClassificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalpullixServiceClientClassificationApplication.class, args);
	}

}
