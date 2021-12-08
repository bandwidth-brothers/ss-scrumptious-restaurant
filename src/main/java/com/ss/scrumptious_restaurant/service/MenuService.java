package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;

import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious.common_entities.entity.MenuCategory;
import com.ss.scrumptious.common_entities.entity.Menuitem;
import com.ss.scrumptious.common_entities.entity.Restaurant;
import com.ss.scrumptious.common_entities.entity.Tag;
public interface MenuService {
	Menuitem createMenuItem(SaveMenuItemDto menuItemDto, Long restaurantId);
    Menuitem getMenuItemById(Long menuId);

    List<Tag> updateMenuItemTag(List<String> tagList, Long menuId);

    List<MenuCategory> updateMenuItemCategory(List<String> categoryList, Long menuId);

    void updateMenuItemById(SaveMenuItemDto dto, Long menuId);

    void deleteMenuItemByIds(List<Long> ids);

    List<Menuitem> getAllMenuItems();
	List<Menuitem> getAllMenuItemsFromRestaurant(Long restaurantId);
	Menuitem getMenuItemFromRestaurant(Long restId, Long itemId);
	List<Menuitem> searchMenuItems(String search);
	List<Menuitem> searchMenuItemsFromRestaurant(String search, Long restaurantId);


	Set<Restaurant> getRestaurantsFromMenuItemSearch(String search);
}
