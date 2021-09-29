package com.ss.scrumptious_restaurant.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/{restaurantId}/menu-items")
	public ResponseEntity<List<MenuItem>> getAllMenuItemsFromRestaurant(@PathVariable Long restaurantId,
			@RequestParam(value = "search", required=false) String search) {
		List<MenuItem> menuItems; 

		if (search == null) {
			menuItems = menuService.getAllMenuItemsFromRestaurant(restaurantId);
		} else {
			menuItems = menuService.searchMenuItemsFromRestaurant(search, restaurantId);
		}

		if (menuItems.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(menuItems);
		}
	}

	@GetMapping("/menu-items")
	public ResponseEntity<List<MenuItem>> getAllMenuItems(@RequestParam(value = "search", required=false) String search) {
		List<MenuItem> menuItems;
		if (search == null) {
			menuItems = menuService.getAllMenuItems();
		} else {
			menuItems = menuService.searchMenuItems(search);
		}

		if (menuItems.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(menuItems);
		}
	}
	
	@GetMapping("/restaurants/menu-items")
	public ResponseEntity<Set<Restaurant>> getRestaurantsFromMenuItemSearch(@RequestParam(value = "search") String search){
		Set<Restaurant> restaurants = menuService.getRestaurantsFromMenuItemSearch(search);
		
		if (restaurants.size() == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(restaurants);
		}
	}

	@GetMapping("/{restId}/menu-items/{itemId}")
	public ResponseEntity<MenuItem> getMenuItemFromRestaurant(@PathVariable Long restId, @PathVariable Long itemId) {
		MenuItem menuItem = menuService.getMenuItemFromRestaurant(restId, itemId);

		return ResponseEntity.ok(menuItem);

	}

}
