package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.MenuItemRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.specification.MenuItemSpecificationsBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

	private RestaurantRepository restaurantRepository;
	private MenuItemRepository menuItemRepository;
	
	@Transactional
	public MenuItem createNewMenuItem(@Valid CreateMenuItemDto createMenuItemDto, Long restaurantId) {
		
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
	
	@Override
	public List<MenuItem> getAllMenuItems() {
		
		return menuItemRepository.findAll();
	}

	@Override
	public List<MenuItem> getAllMenuItemsFromRestaurant(Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		return restaurant.getMenuItems().stream().collect(Collectors.toList());
	}

	

	@Override
	public MenuItem getMenuItemFromRestaurant(Long restId, Long itemId) {
		Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow();

		MenuItem menuItem = restaurant.getMenuItems().stream()
		.filter(item -> item.getId() == itemId)
		.findFirst().orElseThrow();
		
		return menuItem;
	}

	@Override
	public List<MenuItem> searchMenuItems(String search) {
		 MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
	        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	        
	        Specification<MenuItem> spec = builder
	        		.search()
	        		.build();
	        return menuItemRepository.findAll(spec);
	}


	@Override
	public List<MenuItem> searchMenuItemsFromRestaurant(String search, Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		System.out.println(search);
		
		MenuItemSpecificationsBuilder builder = new MenuItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        
        Specification<MenuItem> spec = builder
        		.search()
        		.isFromRestaurant(restaurant)
        		.build();
        
        return menuItemRepository.findAll(spec);
	}

}
