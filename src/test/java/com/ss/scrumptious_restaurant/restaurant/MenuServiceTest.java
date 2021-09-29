package com.ss.scrumptious_restaurant.restaurant;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

//	@Mock
//	RestaurantRepository restaurantRepository;
//	
//	@Mock
//	MenuItemRepository menuItemRepository;
//	
//	
//	private MenuServiceImpl menuServiceImpl;
//		
//	@BeforeEach
//	@Rollback(false)
//	void setUp() {
//		menuServiceImpl = new MenuServiceImpl(restaurantRepository, menuItemRepository);
//	}
//	
//	@Test
//	void createNewRestaurantTest() {
//		
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
//				.build();
//		
//		
//		Mockito.when(restaurantRepository.findById(restaurant.getRestaurantId())).thenReturn(Optional.of(restaurant));
//		
//		CreateMenuItemDto menuItemDto = CreateMenuItemDto.builder()
//				.name("Smoothie Palace")
//				.price(4.5f)
//				.isAvailable(true)
//				.build();
//		
//		MenuItem menuItem = MenuItem.builder()
//				.name(menuItemDto.getName())
//				.price(Money.of(menuItemDto.getPrice(), "USD"))
//				.isAvailable(menuItemDto.getIsAvailable())
//				.restaurant(restaurant)
//				.build();
//		
//		menuServiceImpl.createNewMenuItem(menuItemDto, restaurant.getRestaurantId());
//		
//		ArgumentCaptor<MenuItem> menuItemArgumentCaptor = ArgumentCaptor.forClass(MenuItem.class);
//
//		
//		verify(menuItemRepository).save(menuItemArgumentCaptor.capture());
//
//		MenuItem capturedMenuItem= menuItemArgumentCaptor.getValue();
//
//		assertEquals(menuItem, capturedMenuItem);
//	}
}
