package com.ss.scrumptious_restaurant.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.scrumptious_restaurant.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	/*
	 * Finds a restaurant by using the name as a search criteria.
	 * @param name
	 * @return Restaurant entity whose name is an exact match with the given name.
	 * 			If no Restaurant is found, this method returns null.
	 */
	//@Query("SELECT r FROM Restaurant r WHERE r.name = :name")
	//public Restaurant findByName(@Param("name") String name);
	
}
