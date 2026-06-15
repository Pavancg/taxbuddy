package com.taxbuddy.taxbuddy_eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TaxbuddyEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxbuddyEurekaServerApplication.class, args);
	}

}
