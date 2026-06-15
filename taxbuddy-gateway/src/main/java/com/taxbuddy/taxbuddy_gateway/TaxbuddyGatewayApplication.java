package com.taxbuddy.taxbuddy_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TaxbuddyGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxbuddyGatewayApplication.class, args);
	}

}
