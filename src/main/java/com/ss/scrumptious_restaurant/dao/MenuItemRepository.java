package com.ss.scrumptious_restaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {

	List<MenuItem> findAllByRestaurant(Restaurant r);
	MenuItem findByIdAndRestaurant(Long id, Restaurant r);
    void deleteMenuItemsByIdIn(List<Long> list);
    void deleteByIdIn(List<Long> list);
}
