package com.ss.scrumptious_restaurant.service;

import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

public interface RestaurantOwnerService {


    UUID createNewRestaurantOwner(CreateRestaurantOwnerDto creatRestaurantOwnerDto);

    RestaurantOwner getRestaurantOwnerById(UUID uid);

    RestaurantOwner getRestaurantOwnerByEmail(String email);


    void updateRestaurantOwner(UUID uid, CreateRestaurantOwnerDto updateDto);
}