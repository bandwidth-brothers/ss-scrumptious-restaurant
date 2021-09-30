package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
public interface RestaurantService {
    Long createRestaurant(SaveRestaurantDto createRestaurantDto);
    List<Restaurant> getAllRestaurantsByOwnerId_Owner(UUID uid);
    Restaurant getRestaurantById_Owner(Long rid);
    Set<Cuisine> saveRestaurantCategories_Owner(List<String> categoryList, Long rid);

    void updateRestaurantByOwner_Owner(UUID uid, Long rid, SaveRestaurantDto dto);
    void updateRestaurantById_Owner(Long rid, SaveRestaurantDto dto);

	List<Cuisine> createNewRestaurantCategories(ListRestaurantCategoryDto listRestaurantCategoryDto, Long restaurantId);
	List<Restaurant> getAllRestaurants();
	List<Restaurant> getOwnerRestaurants(UUID ownerId);
	List<RestaurantOwner> getAllRestaurantOwners();
}
