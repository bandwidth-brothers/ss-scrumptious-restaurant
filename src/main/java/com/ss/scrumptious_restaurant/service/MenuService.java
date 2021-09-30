package com.ss.scrumptious_restaurant.service;

import java.util.List;

import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Tag;
public interface MenuService {

	MenuItem createNewMenuItem(SaveMenuItemDto menuItemDto, Long restaurantId);

    Long addMenuItem_Owner(SaveMenuItemDto menuItemDto, Long rid);

    MenuItem getMenuItemById_Owner(Long mid);

    List<MenuItem> getMenuItemByRestaurantId_Owner(Long rid);
	
    List<Tag> addMenuItemTag_Owner(List<String> tagList, Long mid);

    List<MenuCategory> addMenuItemCategory_Owner(List<String> categoryList, Long mid);
	
    void updateMenuItemById_Owner(SaveMenuItemDto dto, Long mid);

    void deleteMenuItemByIds_Owner(List<Long> ids);
    
    List<MenuItem> getAllMenuItems();
	List<MenuItem> getAllMenuItemsFromRestaurant(Long restaurantId);
	MenuItem getMenuItemFromRestaurant(Long restId, Long itemId);
	List<MenuItem> searchMenuItems(String search);
	List<MenuItem> searchMenuItemsFromRestaurant(String search, Long restaurantId);
}
