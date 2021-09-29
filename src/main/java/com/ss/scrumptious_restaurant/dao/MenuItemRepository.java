package com.ss.scrumptious_restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ss.scrumptious_restaurant.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {

}
