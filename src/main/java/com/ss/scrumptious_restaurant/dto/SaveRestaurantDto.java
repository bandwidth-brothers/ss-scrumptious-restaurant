package com.ss.scrumptious_restaurant.dto;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SaveRestaurantDto {
	private final String name;

	private final String lineOne;
	private final String lineTwo;
	private final String city;
	private final String state;
	private final String zip;
	private UUID restaurantOwnerId;
	private final Integer restaurantId;
	private final String phone;
	private final String priceCategory;
	private  final List<String> categories;
}
