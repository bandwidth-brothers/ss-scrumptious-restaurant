package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.Tag;
public interface MenuService {
	MenuItem createMenuItem(SaveMenuItemDto menuItemDto, Long restaurantId);
    MenuItem getMenuItemById(Long menuId);
	
    List<Tag> updateMenuItemTag(List<String> tagList, Long menuId);

    List<MenuCategory> updateMenuItemCategory(List<String> categoryList, Long menuId);
	
    void updateMenuItemById(SaveMenuItemDto dto, Long menuId);

    void deleteMenuItemByIds(List<Long> ids);
    
    List<MenuItem> getAllMenuItems();
	List<MenuItem> getAllMenuItemsFromRestaurant(Long restaurantId);
	MenuItem getMenuItemFromRestaurant(Long restId, Long itemId);
	List<MenuItem> searchMenuItems(String search);
	List<MenuItem> searchMenuItemsFromRestaurant(String search, Long restaurantId);


	Set<Restaurant> getRestaurantsFromMenuItemSearch(String search);
}
