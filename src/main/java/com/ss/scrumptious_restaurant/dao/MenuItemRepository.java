package com.ss.scrumptious_restaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ss.scrumptious_restaurant.entity.Menuitem;
import com.ss.scrumptious_restaurant.entity.Restaurant;

public interface MenuItemRepository extends JpaRepository<Menuitem, Long>, JpaSpecificationExecutor<Menuitem> {

	List<Menuitem> findAllByRestaurant(Restaurant r);
	Menuitem findByIdAndRestaurant(Long id, Restaurant r);
    void deleteMenuItemsByIdIn(List<Long> list);
    void deleteByIdIn(List<Long> list);
}
