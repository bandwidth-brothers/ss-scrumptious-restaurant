package com.ss.scrumptious_restaurant.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious_restaurant.dto.UserIdDto;
import com.ss.scrumptious_restaurant.entity.Admin;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.AdminServiceImpl;
import com.ss.scrumptious_restaurant.service.RestaurantServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", exposedHeaders="Location")
public class RestaurantAdminController {

	private final AdminServiceImpl adminService;
	private final RestaurantServiceImpl restaurantService;
	
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
	
	
	
	
	
	// Get Owner Details
	
	
}
