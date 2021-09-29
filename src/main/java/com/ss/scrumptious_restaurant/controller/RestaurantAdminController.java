package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Menuitem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.service.Impl.MenuServiceImpl;
import com.ss.scrumptious_restaurant.service.Impl.RestaurantServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class RestaurantAdminController {

	private final RestaurantServiceImpl restaurantService;
	private final MenuServiceImpl menuService;

	@GetMapping("/restaurants")
	@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
	public List<Restaurant> getAllRestaurants(){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();

		return restaurants;
	}

	@PostMapping("/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Long> createNewRestaurant(@Valid @RequestBody SaveRestaurantDto createRestaurantDto) {
		Restaurant restaurant = restaurantService.createNewRestaurant(createRestaurantDto);
		long restaurantId = restaurant.getId();
		return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId + "/category-collection")).body(restaurantId);
	}

	@PutMapping(value = "/restaurants/{restaurantId}/category-collection", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Cuisine>> createNewRestaurantCategories(
			@Valid @RequestBody ListRestaurantCategoryDto listRestaurantCategoryDto, @PathVariable Long restaurantId) {
		List<Cuisine> restaurantCategories = restaurantService
				.createNewRestaurantCategories(listRestaurantCategoryDto, restaurantId);
		// List<UUID> restaurantCategoriesIds = restaurantCategories.stream().map(rC ->
		// rC.getRestaurantCategoryId())
		// .collect(Collectors.toList());
		if (restaurantCategories.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId)).body(restaurantCategories);
		}
	}

	@PostMapping("/restaurants/{restaurantId}/menu-items")
	public ResponseEntity<Long> createNewMenuItem(@Valid @RequestBody CreateMenuItemDto createMenuItemDto,
			@PathVariable Long restaurantId) {
		Menuitem menuItem = menuService.createNewMenuItem(createMenuItemDto, restaurantId);
		long menuItemId = menuItem.getId();

		return ResponseEntity.created(URI.create("admin/restaurants/" + restaurantId + "/menu-items")).body(menuItemId);
	}
}
