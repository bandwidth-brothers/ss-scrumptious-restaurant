package com.ss.scrumptious_restaurant.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SaveRestaurantDto {
	@NotNull
	@NotBlank
	private final String name;


	@Builder.Default
	private final Boolean isActive=true;


	private final String lineOne;

	private final String lineTwo;

	@NotNull
	@NotBlank(message = "city cannot be blank.")
	private final String city;

	@NotNull
	@NotBlank(message = "state cannot be blank.")
	private final String state;

	@NotNull
	@NotBlank(message = "zipcode cannot be blank.")
	private final String zip;

	private UUID restaurantOwnerId;
	private final Integer restaurantId;
	private final String phone;
	private final String priceCategory;
	private final List<String> cuisines;
}
