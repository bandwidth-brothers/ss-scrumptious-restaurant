package com.ss.scrumptious_restaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ss.scrumptious_restaurant.dao.AddressRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantCategoryRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantService {

	private AddressRepository addressRepository;
	private RestaurantRepository restaurantRepository;
	private RestaurantCategoryRepository restaurantCategoryRepository;
	private CategoryRepository categoryRepository;

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

		Restaurant restaurant = Restaurant.builder()
				.name(createRestaurantDto.getName())
				.address(address)
				.build();

		restaurantRepository.save(restaurant);

		return restaurant;
	}

	@Transactional
	public List<RestaurantCategory> createNewRestaurantCategories(
			@Valid ListRestaurantCategoryDto listRestaurantCategoryDto, UUID restaurantId) {


		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

		List<RestaurantCategory> restaurantCategories = listRestaurantCategoryDto.getRestaurantCategories()
			.stream()
			.filter(rCDto -> {
				return !categoryRepository.existsRestaurantCategoryByType(rCDto.getType());
			})
			.map( rCDto -> {
				RestaurantCategory rC =  RestaurantCategory.builder()
						.type(rCDto.getType())
						.build();
				restaurant.get().addRestaurantCategory(rC);
				return rC;
			})
			.collect(Collectors.toList());

		restaurantCategoryRepository.saveAll(restaurantCategories);
		restaurantRepository.save(restaurant.get());

		return restaurantCategories;
	}

	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants;
	}
}
