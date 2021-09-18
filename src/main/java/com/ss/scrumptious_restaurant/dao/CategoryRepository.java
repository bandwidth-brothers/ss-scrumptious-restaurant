package com.ss.scrumptious_restaurant.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.RestaurantCategory;

public interface CategoryRepository extends JpaRepository<RestaurantCategory, UUID> {

	boolean existsRestaurantCategoryByType(String type);

}
