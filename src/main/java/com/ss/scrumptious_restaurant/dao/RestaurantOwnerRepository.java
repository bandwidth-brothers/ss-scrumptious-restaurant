package com.ss.scrumptious_restaurant.dao;

import java.util.Optional;
import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.RestaurantOwner;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, UUID>{

    Optional<RestaurantOwner> findByEmail(String email);
}
