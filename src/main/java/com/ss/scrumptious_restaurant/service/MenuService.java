package com.ss.scrumptious_restaurant.service;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.Menuitem;
import com.ss.scrumptious_restaurant.entity.Tag;

import java.util.List;

public interface MenuService {
    Long addMenuItem_Owner(CreateMenuItemDto createMenuItemDto, Long rid);

    Menuitem getMenuItemById_Owner(Long mid);

    List<Menuitem> getMenuItemByRestaurantId_Owner(Long rid);

    List<Tag> addMenuItemTag_Owner(List<String> tagList, Long mid);

    List<MenuCategory> addMenuItemCategory_Owner(List<String> categoryList, Long mid);

    void updateMenuItemById_Owner(SaveMenuItemDto dto, Long mid);

    void deleteMenuItemByIds_Owner(List<Long> ids);
}
