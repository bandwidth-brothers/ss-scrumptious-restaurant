package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.dto.UserIdDto;
import com.ss.scrumptious_restaurant.entity.Admin;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.AdminService;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(exposedHeaders="Location")
public class RestaurantAdminController {

	private final AdminService adminService;
	private final RestaurantService restaurantService;
	private final MenuService menuService;
	
	@PostMapping("/admin/register")
	public ResponseEntity<UUID> createAdmin(
			@Valid @RequestBody CreateAdminDto createAdminDto) {
		System.out.println(createAdminDto);
		UUID uid = adminService.createNewAdmin(createAdminDto);
		return ResponseEntity.of(Optional.ofNullable(uid));
	}
	
	// Get Admin Details
	@GetMapping("/admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Admin> getAdminDetails(@Valid @RequestBody UserIdDto userIdDto){
		Admin admin = adminService.getAdminDetails(userIdDto);

		return ResponseEntity.ok(admin);
	}

	@PostMapping("/restaurants")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Long> createNewRestaurant(@Valid @RequestBody SaveRestaurantDto createRestaurantDto) {
		Long restaurantId = restaurantService.createRestaurant(createRestaurantDto);
		return ResponseEntity.created(URI.create("/admin/restaurants/" + restaurantId + "/category-collection")).body(restaurantId);

	}
	
	@GetMapping("/admin/restaurants")
	@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','CUSTOMER')")
	public ResponseEntity<List<Restaurant>> getAllRestaurants(){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();

		if (restaurants.size() == 0) {
				return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}

	@PutMapping(value = "/restaurants/{restaurantId}/category-collection", consumes = MediaType.APPLICATION_JSON_VALUE)
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
	@PostMapping("/restaurants/{restaurantId}/menu-items")
	public ResponseEntity<Long> createNewMenuItem(@Valid @RequestBody SaveMenuItemDto menuItemDto,
			@PathVariable Long restaurantId) {
		MenuItem menuItem = menuService.createNewMenuItem(menuItemDto, restaurantId);
		long menuItemId = menuItem.getId();

		return ResponseEntity.created(URI.create("admin/restaurants/" + restaurantId + "/menu-items")).body(menuItemId);

	}
	
	
	
	
	
	// Get Owner Details
	
	
}
