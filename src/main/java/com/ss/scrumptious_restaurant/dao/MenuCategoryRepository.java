package com.ss.scrumptious_restaurant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious.common_entities.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    boolean existsMenuCategoryByType(String type);
    Set<MenuCategory> findAllByTypeIn(List<String> list);
}
