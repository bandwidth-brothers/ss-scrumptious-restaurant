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


	/*
	 * Finds a restaurant by using the name as a search criteria.
	 * @param name
	 * @return Restaurant entity whose name is an exact match with the given name.
	 * 			If no Restaurant is found, this method returns null.
	 */
	//@Query("SELECT r FROM Restaurant r WHERE r.name = :name")
	//public Restaurant findByName(@Param("name") String name);

}
