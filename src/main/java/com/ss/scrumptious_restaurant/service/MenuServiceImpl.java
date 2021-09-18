package com.ss.scrumptious_restaurant.service;

import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.MenuItemRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

	private RestaurantRepository restaurantRepository;
	private MenuItemRepository menuItemRepository;
	
	@Transactional
	public MenuItem createNewMenuItem(@Valid CreateMenuItemDto createMenuItemDto, UUID restaurantId) {
		
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		MenuItem menuItem = MenuItem.builder()
				.name(createMenuItemDto.getName())
				.price(Money.of(createMenuItemDto.getPrice(), "USD"))
				.isAvailable(createMenuItemDto.getIsAvailable())
				.restaurant(restaurant)
				.build();
		
		menuItemRepository.save(menuItem);
		
		return menuItem;
	}

}
