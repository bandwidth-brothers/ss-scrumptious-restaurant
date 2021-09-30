package com.ss.scrumptious_restaurant.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.dao.AddressRepository;
import com.ss.scrumptious_restaurant.dao.CuisineRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.mapper.RestaurantDtoMapper;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private AddressRepository addressRepository;
    private RestaurantRepository restaurantRepository;
    private CuisineRepository cuisineRepository;
    private RestaurantOwnerService restaurantOwnerService;

    @Transactional
    @Override
    public Long createRestaurant(SaveRestaurantDto createRestaurantDto) {
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
        return restaurant.getId();
    }

    @Transactional
    public List<Cuisine> createNewRestaurantCategories(
            @Valid ListRestaurantCategoryDto listRestaurantCategoryDto, Long restaurantId) {



        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        List<Cuisine> restaurantCategories = listRestaurantCategoryDto.getRestaurantCategories()
                .stream()
                .filter(rCDto -> {
                    return !cuisineRepository.existsCuisineByType(rCDto.getType());
                })
                .map(rCDto -> {
                    Cuisine rC = Cuisine.builder()
                            .type(rCDto.getType())
                            .build();
                    restaurant.get().addRestaurantCuisine(rC);
                    return rC;
                })
                .collect(Collectors.toList());

        cuisineRepository.saveAll(restaurantCategories);
        restaurantRepository.save(restaurant.get());

        return restaurantCategories;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    

    @Override
    public List<Restaurant> getAllRestaurantsByOwnerId_Owner(UUID uid) {
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerById(uid);
        List<Restaurant> list = restaurantRepository.findByOwner(owner);
        return list;
    }


    @Override
    public Restaurant getRestaurantById_Owner(Long rid) {
        Restaurant re = restaurantRepository.findById(rid).orElseThrow(() -> new NoSuchElementException("restaurant not exist"));
//		re.getRestaurantCategories();
        return re;
    }

    @Transactional
    @Override
    public Set<Cuisine> saveRestaurantCategories_Owner(List<String> categoryList, Long rid) {
        Restaurant restaurant = getRestaurantById_Owner(rid);
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

    @Override
    public void updateRestaurantByOwner_Owner(UUID uid, Long rid, SaveRestaurantDto dto) {
//        RestaurantOwner owner = RestaurantOwner.builder().restaurantOwnerId(dto.getRestaurantOwnerId()).build();
        Restaurant r = getRestaurantById_Owner(rid);
        if(!r.getOwner().getId().equals(uid)){
            throw  new NoSuchElementException("restaurant not exist");
        }else {
            updateRestaurantById_Owner(rid, dto);
        }
    }

    @Transactional
    @Override
    public void updateRestaurantById_Owner(Long rid, SaveRestaurantDto dto) {
        Restaurant r = getRestaurantById_Owner(rid);

        Restaurant updateRestau = RestaurantDtoMapper.map(dto);
        updateRestau.setId(r.getId());
        updateRestau.setOwner(r.getOwner());
        updateRestau.setRating(r.getRating());

        restaurantRepository.save(updateRestau);
        saveRestaurantCategories_Owner(dto.getCategories(), rid);
    }

    public List<Restaurant> getOwnerRestaurants(UUID ownerId) {
		RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwnerById(ownerId);
				
		return restaurantRepository.findByOwner(restaurantOwner);
	}

	public List<RestaurantOwner> getAllRestaurantOwners() {
		List<RestaurantOwner> RestaurantOwners = restaurantOwnerService.getAllRestaurantOwners();
		return RestaurantOwners;
		}
}
