package com.ss.scrumptious_restaurant.service;

import com.ss.scrumptious_restaurant.dto.CreatRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

import java.util.UUID;

public interface RestaurantOwnerService {


    UUID createNewRestaurantOwner(CreatRestaurantOwnerDto creatRestaurantOwnerDto);

    RestaurantOwner getRestaurantOwnerById(UUID uid);

    RestaurantOwner getRestaurantOwnerByEmail(String email);

    void updateRestaurantOwner(UUID uid, UpdateRestaurantOwnerDto updateDto);
}
