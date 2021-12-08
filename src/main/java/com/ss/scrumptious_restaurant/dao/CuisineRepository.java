package com.ss.scrumptious_restaurant.dao;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious.common_entities.entity.Cuisine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	boolean existsCuisineByType(String type);
	Set<Cuisine> findByTypeIn(List<String> list);
	Cuisine findByType(String s);

}
