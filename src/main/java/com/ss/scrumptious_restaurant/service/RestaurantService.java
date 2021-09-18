package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

public interface RestaurantService {

	Restaurant createNewRestaurant(CreateRestaurantDto createRestaurantDto);
	List<RestaurantCategory> createNewRestaurantCategories(ListRestaurantCategoryDto listRestaurantCategoryDto, UUID restaurantId);
	List<Restaurant> getAllRestaurants();
	Set<Restaurant> getOwnerRestaurants(UUID ownerId);
	List<RestaurantOwner> getAllRestaurantOwners();
}
