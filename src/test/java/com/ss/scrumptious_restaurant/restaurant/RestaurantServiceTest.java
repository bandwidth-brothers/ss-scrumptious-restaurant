package com.ss.scrumptious_restaurant.restaurant;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
//	CuisineRepository restaurantCategoryRepository;
//	
//	@Mock
//	RestaurantOwnerRepository restaurantOwnerRepository;
//	
//	@Mock
//	CategoryRepository categoryRepository;
//	
//	@Captor
//	private ArgumentCaptor<List<Cuisine>> captor;
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
//		Cuisine catOne =  Cuisine.builder()
//				.type(catOneDto.getType())
//				.build();
//		
//		Cuisine catTwo =  Cuisine.builder()
//				.type(catTwoDto.getType())
//				.build();
//		List<Cuisine> restaurantCategories = new ArrayList<>(Arrays.asList(catOne, catTwo));
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
//		List<Cuisine> capturedCategories = captor.getValue();
//		Restaurant capturedRestaurant = restaurantArgumentCaptor.getValue();
//		
//		
//		assertEquals(restaurantCategories, capturedCategories);
//		assertEquals(restaurant, capturedRestaurant);
		
//	}
	
}
