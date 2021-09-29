package com.ss.scrumptious_restaurant.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, UUID>{

	Optional<RestaurantOwner> findByEmail(String email);
}
