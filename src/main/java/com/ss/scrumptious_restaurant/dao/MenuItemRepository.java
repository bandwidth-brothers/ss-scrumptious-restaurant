package com.ss.scrumptious_restaurant.dao;

import java.util.List;

import com.ss.scrumptious_restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.Menuitem;

public interface MenuItemRepository extends JpaRepository<Menuitem, Long> {

     List<Menuitem> findAllByRestaurant(Restaurant r);
     void deleteMenuItemsByIdIn(List<Long> list);
     void deleteByIdIn(List<Long> list);
}
