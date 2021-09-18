package com.ss.scrumptious_restaurant.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/restaurant")
@CrossOrigin(origins="*", exposedHeaders="Location")
public class RestaurantOwnerController {
	private final RestaurantOwnerServiceImpl restaurantOwnerService;



	@PostMapping("/owner/register")
	public ResponseEntity<UUID> createRestaurantOwner(
			@Valid @RequestBody CreateRestaurantOwnerDto createRestaurantOwnerDto) {
		System.out.println(createRestaurantOwnerDto);
		UUID uid = restaurantOwnerService.createNewRestaurantOwner(createRestaurantOwnerDto);
		return ResponseEntity.of(Optional.ofNullable(uid));
	}

	@GetMapping("/owner/{uid}")
	@PreAuthorize("hasAnyRole('EMPLOYEE')")
	public ResponseEntity<RestaurantOwner> getRestaurantOwnerById(@PathVariable UUID uid) {
		RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerById(uid);
		return ResponseEntity.of(Optional.ofNullable(owner));
	}

	@GetMapping("/owner/email/{email}")
	@PreAuthorize("hasAnyRole('EMPLOYEE')")
	public ResponseEntity<RestaurantOwner> getRestaurantOwnerByEmail(@PathVariable String email) {
		RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerByEmail(email);
		return ResponseEntity.of(Optional.ofNullable(owner));
	}

	@PutMapping("/owner/{uid}")
	@PreAuthorize("hasAnyRole('EMPLOYEE')")
	public ResponseEntity<Void> updateRestaurantOwner(@PathVariable UUID uid,
			@Valid @RequestBody CreateRestaurantOwnerDto updateDto) {

		restaurantOwnerService.updateRestaurantOwner(uid, updateDto);
		return ResponseEntity.noContent().build();
	}
	
	// Restaurant Owner
		

}