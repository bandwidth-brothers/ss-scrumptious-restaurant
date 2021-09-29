package com.ss.scrumptious_restaurant.service;

import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.Cuisine;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RestaurantService {
    Long createRestaurant_Owner(SaveRestaurantDto createRestaurantDto);

    List<Restaurant> getAllRestaurantsByOwnerId_Owner(UUID uid);

    Restaurant getRestaurantById_Owner(Long rid);

    Set<Cuisine> saveRestaurantCategories_Owner(List<String> categoryList, Long rid);

    void updateRestaurantByOwner_Owner(UUID uid, Long rid, SaveRestaurantDto dto);

    void updateRestaurantById_Owner(Long rid, SaveRestaurantDto dto);
}
