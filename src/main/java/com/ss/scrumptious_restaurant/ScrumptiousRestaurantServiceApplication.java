package com.ss.scrumptious_restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EntityScan(basePackages = { "com.ss.scrumptious.common_entities.entity" })
@SpringBootApplication
public class ScrumptiousRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumptiousRestaurantServiceApplication.class, args);
	}

	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
