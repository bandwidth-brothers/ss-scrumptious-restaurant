package com.ss.scrumptious_restaurant.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.javamoney.moneta.Money;
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
import com.ss.scrumptious_restaurant.dao.RestaurantCategoryRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.CreateMenuItemDto;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantCategory;
import com.ss.scrumptious_restaurant.service.CategoryRepository;
import com.ss.scrumptious_restaurant.service.MenuItemRepository;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

	@Mock
	RestaurantRepository restaurantRepository;
	
	@Mock
	MenuItemRepository menuItemRepository;
	
	
	private MenuService menuService;
		
	@BeforeEach
	@Rollback(false)
	void setUp() {
		menuService = new MenuService(restaurantRepository, menuItemRepository);
	}
	
	@Test
	void createNewRestaurantTest() {
		Address address = Address.builder()
				.lineOne("123 Main St.")
				.city("Fairfax")
				.state("VA")
				.zip("22031")
				.build();
		
		Restaurant restaurant = Restaurant.builder()
				.restaurantId(UUID.randomUUID())
				.name("Juice World")
				.address(address)
				.build();
		
		
		Mockito.when(restaurantRepository.findById(restaurant.getRestaurantId())).thenReturn(Optional.of(restaurant));
		
		CreateMenuItemDto menuItemDto = CreateMenuItemDto.builder()
				.name("Smoothie Palace")
				.price(4.5f)
				.isAvailable(true)
				.build();
		
		MenuItem menuItem = MenuItem.builder()
				.name(menuItemDto.getName())
				.price(Money.of(menuItemDto.getPrice(), "USD"))
				.isAvailable(menuItemDto.getIsAvailable())
				.restaurant(restaurant)
				.build();
		
		menuService.createNewMenuItem(menuItemDto, restaurant.getRestaurantId());
		
		ArgumentCaptor<MenuItem> menuItemArgumentCaptor = ArgumentCaptor.forClass(MenuItem.class);

		
		verify(menuItemRepository).save(menuItemArgumentCaptor.capture());

		MenuItem capturedMenuItem= menuItemArgumentCaptor.getValue();

		assertEquals(menuItem, capturedMenuItem);
	}
}
