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
import com.ss.scrumptious_restaurant.dao.CuisineRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

	private AddressRepository addressRepository;
	private RestaurantRepository restaurantRepository;
	private CuisineRepository cuisineRepository;
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
				.owner(restaurantOwner)
				.build();
				
		restaurantRepository.save(restaurant);
		
		return restaurant;
	}

	@Transactional
	public List<Cuisine> createNewRestaurantCategories(
			@Valid ListRestaurantCategoryDto listRestaurantCategoryDto, Long restaurantId) {
		
		
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		// TODO: Make Category Unique so Different restaurants could associate with category	
		List<Cuisine> cuisines = listRestaurantCategoryDto.getRestaurantCategories()
			.stream()
			.filter(rCDto -> {
				return !cuisineRepository.existsRestaurantCategoryByType(rCDto.getType());
			})
			.map( rCDto -> {
				Cuisine rC =  Cuisine.builder()
						.type(rCDto.getType())
						.build();
				restaurant.addRestaurantCuisine(rC);
				return rC;				
			})
			.collect(Collectors.toList());
		
		cuisineRepository.saveAll(cuisines);
		restaurantRepository.save(restaurant);
		
		return cuisines;
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
