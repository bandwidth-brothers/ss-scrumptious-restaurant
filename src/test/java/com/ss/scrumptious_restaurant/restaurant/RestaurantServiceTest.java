package com.ss.scrumptious_restaurant.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.ss.scrumptious_restaurant.dao.AddressRepository;
import com.ss.scrumptious_restaurant.dao.CategoryRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantCategoryRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.dto.ListRestaurantCategoryDto;
import com.ss.scrumptious_restaurant.dto.RestaurantCategoryDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.RestaurantServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

//	@Mock
//	RestaurantRepository restaurantRepository;
//	
//	@Mock
//	AddressRepository addressRepository;
//	
//	@Mock
//	RestaurantCategoryRepository restaurantCategoryRepository;
//	
//	@Mock
//	RestaurantOwnerRepository restaurantOwnerRepository;
//	
//	@Mock
//	CategoryRepository categoryRepository;
//	
//	@Captor
//	private ArgumentCaptor<List<RestaurantCategory>> captor;
//	
//	private RestaurantServiceImpl restaurantServiceImpl;
//		
//	@BeforeEach
//	@Rollback(false)
//	void setUp() {
//		restaurantServiceImpl = new RestaurantServiceImpl(addressRepository, restaurantRepository, restaurantCategoryRepository, categoryRepository, restaurantOwnerRepository);
//
//	}
	
//	@Test
//	void createNewRestaurantTest() {
//		RestaurantOwner restaurantOwner = RestaurantOwner.builder()
//				.restaurantOwnerId(UUID.randomUUID())
//				.firstName("John")
//				.lastName("Brown")
//				.phone("(111)222-3333")
//				.email("owner.brown@gmail.com")
//				.build();
//		
//		CreateRestaurantDto restaurantDto = CreateRestaurantDto.builder()
//				.name("Smoothie Palace")
//				.lineOne("123 Main St.")
//				.city("Fairfax")
//				.state("VA")
//				.zip("22031")
//				.restaurantOwnerId(restaurantOwner.getRestaurantOwnerId())
//				.build();
//		
//		Address address = Address.builder()
//				.lineOne(restaurantDto.getLineOne())
//				.lineTwo(restaurantDto.getLineTwo() != null ? restaurantDto.getLineTwo() : null)
//				.city(restaurantDto.getCity())
//				.state(restaurantDto.getState())
//				.zip(restaurantDto.getZip())
//				.build();
//		
//		Restaurant restaurant = Restaurant.builder()
//				.name(restaurantDto.getName())
//				.address(address)
//				.restaurantOwner(restaurantOwner)
//				.build();
//		
//		
//		
//		Mockito.lenient().when(restaurantOwnerRepository.findById(restaurantOwner.getRestaurantOwnerId())).thenReturn(Optional.of(restaurantOwner));
//		
//		restaurantServiceImpl.createNewRestaurant(restaurantDto);
//		
//		ArgumentCaptor<Address> addressArgumentCaptor = ArgumentCaptor.forClass(Address.class);
//		ArgumentCaptor<Restaurant> restaurantArgumentCaptor = ArgumentCaptor.forClass(Restaurant.class);
//
//		
//		verify(addressRepository).save(addressArgumentCaptor.capture());
//		verify(restaurantRepository).save(restaurantArgumentCaptor.capture());
//
//		Address capturedAddress = addressArgumentCaptor.getValue();
//		Restaurant capturedRestaurant = restaurantArgumentCaptor.getValue();
//
//		assertEquals(address, capturedAddress);
//		assertEquals(restaurant, capturedRestaurant);
//	}
	
	
//	@Test
//	void createNewRestaurantCategoriesTest() {
		
//		Address address = Address.builder()
//				.lineOne("123 Main St.")
//				.city("Fairfax")
//				.state("VA")
//				.zip("22031")
//				.build();
//			
//		Restaurant restaurant = Restaurant.builder()
//				.restaurantId(UUID.randomUUID())
//				.name("Juice World")
//				.address(address)
//				.restaurantCategories(new HashSet<>())
//				.build();
//		
//
//		Mockito.when(restaurantRepository.findById(restaurant.getRestaurantId())).thenReturn(Optional.of(restaurant));
//		
//		RestaurantCategoryDto catOneDto = RestaurantCategoryDto.builder()
//				.type("Fast Food")
//				.build();
//		
//		RestaurantCategoryDto catTwoDto = RestaurantCategoryDto.builder()
//				.type("Drinks")
//				.build();
//		
//		RestaurantCategory catOne =  RestaurantCategory.builder()
//				.type(catOneDto.getType())
//				.build();
//		
//		RestaurantCategory catTwo =  RestaurantCategory.builder()
//				.type(catTwoDto.getType())
//				.build();
//		List<RestaurantCategory> restaurantCategories = new ArrayList<>(Arrays.asList(catOne, catTwo));
//		
//		List<RestaurantCategoryDto> listCategory = new ArrayList<>(Arrays.asList(catOneDto, catTwoDto));
//		ListRestaurantCategoryDto listRestaurantCategoryDto = ListRestaurantCategoryDto.builder()
//				.restaurantCategories(listCategory)
//				.build();
//		
//		restaurantServiceImpl.createNewRestaurantCategories(listRestaurantCategoryDto, restaurant.getRestaurantId());
//		
//		ArgumentCaptor<Restaurant> restaurantArgumentCaptor = ArgumentCaptor.forClass(Restaurant.class);
//
//		
//		verify(restaurantCategoryRepository).saveAll(captor.capture());
//		verify(restaurantRepository).save(restaurantArgumentCaptor.capture());
//		
//		List<RestaurantCategory> capturedCategories = captor.getValue();
//		Restaurant capturedRestaurant = restaurantArgumentCaptor.getValue();
//		
//		
//		assertEquals(restaurantCategories, capturedCategories);
//		assertEquals(restaurant, capturedRestaurant);
		
//	}
	
}
