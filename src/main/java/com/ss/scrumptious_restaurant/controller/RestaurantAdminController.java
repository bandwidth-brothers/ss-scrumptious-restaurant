package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RestaurantAdminController {

	private final RestaurantService restaurantService;

	@GetMapping("/restaurants")
	@PreAuthorize("hasRole('CUSTOMER')")
	public List<Restaurant> getAllRestaurants(){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();

		return restaurants;
	}
	
	@PostMapping("/restaurants")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<UUID> createNewRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
		Restaurant restaurant = restaurantService.createNewRestaurant(createRestaurantDto);
		UUID restaurantId = restaurant.getRestaurantId();
		return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId + "/category-collection")).body(restaurantId);
	}

	@PutMapping(value = "/restaurants/{restaurantId}/category-collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RestaurantCategory>> createNewRestaurantCategories(
			@Valid @RequestBody ListRestaurantCategoryDto listRestaurantCategoryDto, @PathVariable UUID restaurantId) {
		List<RestaurantCategory> restaurantCategories = restaurantService
				.createNewRestaurantCategories(listRestaurantCategoryDto, restaurantId);
		//List<UUID> restaurantCategoriesIds = restaurantCategories.stream().map(rC -> rC.getRestaurantCategoryId())
				//.collect(Collectors.toList());
		if (restaurantCategories.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId)).body(restaurantCategories);
		}
	}
}
