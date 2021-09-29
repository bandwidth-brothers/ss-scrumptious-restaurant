package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(exposedHeaders="Location")
public class OwnerAdminController {

	private final RestaurantService restaurantService;
	private final MenuService menuService;

	
	@GetMapping("/owner/{ownerId}/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Set<Restaurant>> getOwnerRestaurants(@PathVariable UUID ownerId) {
		Set<Restaurant> restaurants = restaurantService.getOwnerRestaurants(ownerId);
		
		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}
	
	@PostMapping("/admin/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Long> createNewRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
		Restaurant restaurant = restaurantService.createNewRestaurant(createRestaurantDto);
		Long restaurantId = restaurant.getId();
		return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId + "/category-collection")).body(restaurantId);
	}

	@PutMapping(value = "/admin/restaurants/{restaurantId}/category-collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Cuisine>> createNewRestaurantCategories(
			@Valid @RequestBody ListRestaurantCategoryDto listRestaurantCategoryDto, @PathVariable Long restaurantId) {
		List<Cuisine> cuisines = restaurantService
				.createNewRestaurantCategories(listRestaurantCategoryDto, restaurantId);

		if (cuisines.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId)).body(cuisines);
		}
	}

	@PostMapping("/admin/restaurants/{restaurantId}/menu-items")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Long> createNewMenuItem(@Valid @RequestBody CreateMenuItemDto createMenuItemDto,
			@PathVariable Long restaurantId) {
		MenuItem menuItem = menuService.createNewMenuItem(createMenuItemDto, restaurantId);
		Long menuItemId = menuItem.getId();

		return ResponseEntity.created(URI.create("admin/restaurants/" + restaurantId + "/menu-items")).body(menuItemId);
	}
}
