package com.ss.scrumptious_restaurant.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	boolean existsCuisineByType(String type);
	Set<Cuisine> findByTypeIn(List<String> list);
	Cuisine findByType(String s);

}
