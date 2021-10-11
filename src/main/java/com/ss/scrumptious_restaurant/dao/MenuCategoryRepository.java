package com.ss.scrumptious_restaurant.dao;

import com.ss.scrumptious_restaurant.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    boolean existsMenuCategoryByType(String type);
    Set<MenuCategory> findAllByTypeIn(List<String> list);
}
