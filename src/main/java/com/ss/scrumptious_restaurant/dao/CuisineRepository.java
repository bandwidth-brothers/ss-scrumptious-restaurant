package com.ss.scrumptious_restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	boolean existsRestaurantCategoryByType(String type);
}
