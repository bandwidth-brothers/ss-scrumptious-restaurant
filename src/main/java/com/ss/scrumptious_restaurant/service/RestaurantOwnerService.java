package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious.common_entities.entity.RestaurantOwner;

public interface RestaurantOwnerService {


    UUID createNewRestaurantOwner(CreateRestaurantOwnerDto creatRestaurantOwnerDto);

    RestaurantOwner getRestaurantOwnerById(UUID uid);

    RestaurantOwner getRestaurantOwnerByEmail(String email);

    List<RestaurantOwner> getAllRestaurantOwners();
    
    void updateRestaurantOwner(UUID uid, UpdateRestaurantOwnerDto updateDto);
}