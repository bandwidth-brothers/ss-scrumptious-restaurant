package com.ss.scrumptious_restaurant.dao;

import java.util.List;

import com.ss.scrumptious.common_entities.entity.Cuisine;
import com.ss.scrumptious.common_entities.entity.Restaurant;
import com.ss.scrumptious.common_entities.entity.RestaurantOwner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findByOwner(RestaurantOwner owner);
	List<Restaurant> findByCuisines(Cuisine category);

}
