package com.ss.scrumptious_restaurant.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious.common_entities.entity.Admin;
import com.ss.scrumptious.common_entities.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious_restaurant.service.AdminService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

	private final AdminService adminService;
	private final RestaurantService restaurantService;

	@PostMapping("/register")
	public ResponseEntity<UUID> createAdmin(
			@Valid @RequestBody CreateAdminDto createAdminDto) {
		UUID uid = adminService.createNewAdmin(createAdminDto);
		return ResponseEntity.of(Optional.ofNullable(uid));
	}

	@GetMapping("/{adminId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Admin> getAdminById(@PathVariable UUID adminId){
		Admin admin = adminService.getAdminById(adminId);

		return ResponseEntity.ok(admin);
	}

	@GetMapping("/owners")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<RestaurantOwner>> getAllRestaurantOwners() {
		List<RestaurantOwner> restaurantOwners = restaurantService.getAllRestaurantOwners();

		if (restaurantOwners.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurantOwners);
		}

	}


	@GetMapping("/restaurants/{restaurantId}/owner")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<RestaurantOwner> getOwnerByRestaurantId(@PathVariable Long restaurantId) {
		RestaurantOwner owner = restaurantService.getOwnerByRestaurantId(restaurantId);

		return ResponseEntity.ok(owner);
	}
}
