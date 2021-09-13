package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", exposedHeaders="Location")
public class RestaurantAdminController {

	private final RestaurantService restaurantService;
	private final MenuService menuService;

	@GetMapping("/admin/restaurants")
	@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
	public ResponseEntity<List<Restaurant>> getAllRestaurants(){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();

		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}
	
	@GetMapping("/admin/owners")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<RestaurantOwner>> getAllRestaurantOwners() {
		List<RestaurantOwner> restaurantOwners = restaurantService.getAllRestaurantOwners();

		if (restaurantOwners.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurantOwners);
		}
	}
	
	@PostMapping("/admin/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<UUID> createNewRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
		Restaurant restaurant = restaurantService.createNewRestaurant(createRestaurantDto);
		UUID restaurantId = restaurant.getRestaurantId();
		return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId + "/category-collection")).body(restaurantId);
	}

	@PutMapping(value = "/admin/restaurants/{restaurantId}/category-collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<RestaurantCategory>> createNewRestaurantCategories(
			@Valid @RequestBody ListRestaurantCategoryDto listRestaurantCategoryDto, @PathVariable UUID restaurantId) {
		List<RestaurantCategory> restaurantCategories = restaurantService
				.createNewRestaurantCategories(listRestaurantCategoryDto, restaurantId);

		if (restaurantCategories.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId)).body(restaurantCategories);
		}
	}

	@PostMapping("/admin/restaurants/{restaurantId}/menu-items")
	public ResponseEntity<UUID> createNewMenuItem(@Valid @RequestBody CreateMenuItemDto createMenuItemDto,
			@PathVariable UUID restaurantId) {
		MenuItem menuItem = menuService.createNewMenuItem(createMenuItemDto, restaurantId);
		UUID menuItemId = menuItem.getMenuItemId();

		return ResponseEntity.created(URI.create("admin/restaurants/" + restaurantId + "/menu-items")).body(menuItemId);
	}
	
	// Restaurant Owner
	@GetMapping("/owner/{ownerId}/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<Set<Restaurant>> getOwnerRestaurants(@PathVariable UUID ownerId) {
		Set<Restaurant> restaurants = restaurantService.getOwnerRestaurants(ownerId);
		
		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}
}
