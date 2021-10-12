package com.ss.scrumptious_restaurant.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.AddressRepository;
import com.ss.scrumptious_restaurant.dao.CuisineRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.mapper.RestaurantDtoMapper;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.AllArgsConstructor;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private AddressRepository addressRepository;
    private RestaurantRepository restaurantRepository;
    private CuisineRepository cuisineRepository;
    private RestaurantOwnerService restaurantOwnerService;

    @Transactional
    @Override
    public Restaurant createRestaurant(SaveRestaurantDto createRestaurantDto) {
        Address address = Address.builder()
                .lineOne(createRestaurantDto.getLineOne())
                .lineTwo(createRestaurantDto.getLineTwo())
                .city(createRestaurantDto.getCity())
                .state(createRestaurantDto.getState())
                .zip(createRestaurantDto.getZip())
                .build();

        Address adr = addressRepository.save(address);

        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerById(createRestaurantDto.getRestaurantOwnerId());

        Restaurant restaurant = Restaurant.builder()
                .owner(owner)
                .name(createRestaurantDto.getName())
                .address(adr)
                .build();

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    public List<Restaurant> getOwnerRestaurants(UUID ownerId) {
		RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwnerById(ownerId);
		return restaurantRepository.findByOwner(restaurantOwner);
	}

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        Restaurant re = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NoSuchElementException("restaurant not exist"));
        return re;
    }

    @Transactional
    @Override
    public Set<Cuisine> updateRestaurantCuisines(List<String> categoryList, Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        //find unexist category and save them
        List<Cuisine> list = categoryList.stream()
                .filter(s -> !cuisineRepository.existsCuisineByType(s))
                .map(s -> Cuisine.builder().type(s).build())
                .collect(Collectors.toList());
        cuisineRepository.saveAll(list);

        //fetch all the category from db
        Set<Cuisine> all = cuisineRepository.findByTypeIn(categoryList);

        //set the restaurant category and save it.
        restaurant.setCuisines(all);
        restaurantRepository.save(restaurant);
        return all;
    }

    @Transactional
    @Override
    public void updateRestaurantById(Long restaurantId, SaveRestaurantDto dto) {
        Restaurant r = getRestaurantById(restaurantId);

        Restaurant updateRestaurant = RestaurantDtoMapper.map(dto);
        updateRestaurant.setId(r.getId());
        updateRestaurant.setOwner(r.getOwner());
        updateRestaurant.setRating(r.getRating());

        restaurantRepository.save(updateRestaurant);
        updateRestaurantCuisines(dto.getCuisines(), restaurantId);
    }



	public List<RestaurantOwner> getAllRestaurantOwners() {
		List<RestaurantOwner> RestaurantOwners = restaurantOwnerService.getAllRestaurantOwners();
		return RestaurantOwners;
		}


    @Override
    public void deactivateRestaurantById(Long rid) {
        Restaurant r = restaurantRepository.findById(rid).orElseThrow(() -> new NoSuchElementException("restaurant not exist" + rid));
        try{
            r.setIsActive(false);
            restaurantRepository.save(r);
        }catch (Exception e){
            log.error("deActiveRestaurantById: " + rid + " " + e.getMessage());
        }

    }

	
	@Override
	public RestaurantOwner getOwnerByRestaurantId(Long restaurantId) {
		Restaurant restaurant = getRestaurantById(restaurantId);
		return restaurant.getOwner();
	}	

}
