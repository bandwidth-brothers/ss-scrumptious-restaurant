package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;

public interface MenuService {

	MenuItem createNewMenuItem(CreateMenuItemDto createMenuItemDto, Long restaurantId);


	List<MenuItem> getAllMenuItems();
	List<MenuItem> getAllMenuItemsFromRestaurant(Long restaurantId);

	

	MenuItem getMenuItemFromRestaurant(Long restId, Long itemId);


	List<MenuItem> searchMenuItems(String search);
	List<MenuItem> searchMenuItemsFromRestaurant(String search, Long restaurantId);


	Set<Restaurant> getRestaurantsFromMenuItemSearch(String search);

}
