package com.ss.scrumptious_restaurant.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CreateRestaurantDto {
	private final String name;
	
	private final String lineOne;
	private final String lineTwo;
	private final String city;
	private final String state;
	private final String zip;
	
	private final UUID restaurantOwnerId;
}
