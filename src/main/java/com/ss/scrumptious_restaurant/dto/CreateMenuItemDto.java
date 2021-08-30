package com.ss.scrumptious_restaurant.dto;

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
public class CreateMenuItemDto {

	private final String name;
	private final Float price;
	private final Boolean isAvailable;
}
