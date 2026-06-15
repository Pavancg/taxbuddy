package com.taxbuddy.taxbuddy_tax_data_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TaxbuddyTaxDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxbuddyTaxDataServiceApplication.class, args);
	}

}
