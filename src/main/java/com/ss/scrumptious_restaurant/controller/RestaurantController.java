package com.ss.scrumptious_restaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantService restaurantService;
	private final MenuService menuService;

	@PostMapping("/restaurants")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #restaurantDto)")
	public ResponseEntity<Void> createRestaurant(@Valid @RequestBody SaveRestaurantDto restaurantDto) {
		Restaurant restaurant = restaurantService.createRestaurant(restaurantDto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{restaurantId}")
				.buildAndExpand(restaurant.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/restaurants")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<List<Restaurant>> getAllRestaurants(){
		List<Restaurant> restaurants = restaurantService.getAllRestaurants();

		if (restaurants.size() == 0) {
				return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}
	
	@GetMapping("/restaurants/menu-items")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<Set<Restaurant>> getRestaurantsFromMenuItemSearch(@RequestParam(value = "search") String search){
		Set<Restaurant> restaurants = menuService.getRestaurantsFromMenuItemSearch(search);
		
		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}
	
	@GetMapping("/owners/{ownerId}/restaurants")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
	public ResponseEntity<List<Restaurant>> getAllRestaurantsWithOwner(@PathVariable UUID ownerId) {
		List<Restaurant> restaurants = restaurantService.getOwnerRestaurants(ownerId);

		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}

	@GetMapping("/owners/{ownerId}/restaurants/{restaurantId}")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
	public ResponseEntity<Restaurant> getRestaurantByIdWithOwner(@PathVariable UUID ownerId, @PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		return ResponseEntity.ok(restaurant);
	}

	@GetMapping("/restaurants/{restaurantId}")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN','CUSTOMER')")
	public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		return ResponseEntity.ok(restaurant);
	}
	
	
	@PutMapping("/owners/{ownerId}/restaurants/{restaurantId}")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
	public ResponseEntity<Void> updateRestaurantByIdWithOwner(@PathVariable UUID ownerId, @PathVariable Long restaurantId,
			@Valid @RequestBody SaveRestaurantDto dto) {
		restaurantService.updateRestaurantById(restaurantId, dto);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * json format array: ["bbq", "bar", "pizza"]
	 * 
	 * @param ownerId
	 * @param restaurantId
	 * @param categoryList
	 * @return
	 */
	@PutMapping(value = "/owners/{ownerId}/restaurants/{restaurantId}/cuisines")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
	public ResponseEntity<Set<Cuisine>> updateRestaurantCuisinesWithOwner(@PathVariable UUID ownerId,
			@PathVariable Long restaurantId, @RequestBody List<String> categoryList) {
		Set<Cuisine> restaurantCuisines = restaurantService.updateRestaurantCuisines(categoryList, restaurantId);

		if (restaurantCuisines.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(restaurantCuisines);
		}
	}

}
