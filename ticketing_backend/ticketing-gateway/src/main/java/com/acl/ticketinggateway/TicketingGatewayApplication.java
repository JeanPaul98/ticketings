package com.acl.ticketinggateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TicketingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingGatewayApplication.class, args);
    }

}
