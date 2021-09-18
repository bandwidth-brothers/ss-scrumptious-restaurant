package com.ss.scrumptious_restaurant.service;

import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;

public interface MenuService {

	MenuItem createNewMenuItem(CreateMenuItemDto createMenuItemDto, UUID restaurantId);

}
