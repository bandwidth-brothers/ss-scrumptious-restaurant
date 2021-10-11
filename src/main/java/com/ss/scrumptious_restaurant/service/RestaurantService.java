package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
public interface RestaurantService {
    Restaurant createRestaurant(SaveRestaurantDto createRestaurantDto);
    Restaurant getRestaurantById(Long restaurantId);
    Set<Cuisine> updateRestaurantCuisines(List<String> cuisineList, Long restaurantId);

    void updateRestaurantById(Long restaurantId, SaveRestaurantDto dto);

	List<Restaurant> getAllRestaurants();
	List<Restaurant> getOwnerRestaurants(UUID ownerId);
	RestaurantOwner getOwnerByRestaurantId(Long restaurantId);
	List<RestaurantOwner> getAllRestaurantOwners();
}
