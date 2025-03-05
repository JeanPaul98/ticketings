package com.acl.ticketingdiscorvery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TicketingdiscorveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingdiscorveryApplication.class, args);
    }

}
