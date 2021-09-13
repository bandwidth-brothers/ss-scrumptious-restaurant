package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ss.scrumptious_restaurant.dao.AddressRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantCategoryRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantService {

	private AddressRepository addressRepository;
	private RestaurantRepository restaurantRepository;
	private RestaurantCategoryRepository restaurantCategoryRepository;
	private CategoryRepository categoryRepository;
	private RestaurantOwnerRepository restaurantOwnerRepository;
	
	@Transactional
	public Restaurant createNewRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
		Address address = Address.builder()
				.lineOne(createRestaurantDto.getLineOne())
				.lineTwo(createRestaurantDto.getLineTwo() != null ? createRestaurantDto.getLineTwo() : null)
				.city(createRestaurantDto.getCity())
				.state(createRestaurantDto.getState())
				.zip(createRestaurantDto.getZip())
				.build();
		
		
		addressRepository.save(address);
		
		RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(createRestaurantDto.getRestaurantOwnerId()).orElseThrow();
		
		Restaurant restaurant = Restaurant.builder()
				.name(createRestaurantDto.getName())
				.address(address)
				.restaurantOwner(restaurantOwner)
				.build();
				
		restaurantRepository.save(restaurant);
		
		return restaurant;
	}

	@Transactional
	public List<RestaurantCategory> createNewRestaurantCategories(
			@Valid ListRestaurantCategoryDto listRestaurantCategoryDto, UUID restaurantId) {
		
		
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		// TODO: Make Category Unique so Different restaurants could associate with category	
		List<RestaurantCategory> restaurantCategories = listRestaurantCategoryDto.getRestaurantCategories()
			.stream()
			.filter(rCDto -> {
				return !categoryRepository.existsRestaurantCategoryByType(rCDto.getType());
			})
			.map( rCDto -> {
				RestaurantCategory rC =  RestaurantCategory.builder()
						.type(rCDto.getType())
						.build();
				restaurant.addRestaurantCategory(rC);
				return rC;				
			})
			.collect(Collectors.toList());
		
		restaurantCategoryRepository.saveAll(restaurantCategories);
		restaurantRepository.save(restaurant);
		
		return restaurantCategories;
	}

	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants;
	}

	
	public Set<Restaurant> getOwnerRestaurants(UUID ownerId) {
		RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(ownerId).orElseThrow();
		
		Set<Restaurant> restaurants = restaurantOwner.getRestaurants();
		
		return restaurants;
	}

	public List<RestaurantOwner> getAllRestaurantOwners() {
		List<RestaurantOwner> RestaurantOwners = restaurantOwnerRepository.findAll();
		return RestaurantOwners;
		}
}
