package com.ss.scrumptious_restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ScrumptiousRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumptiousRestaurantServiceApplication.class, args);
	}

}
