package com.ss.scrumptious_restaurant.service.Impl;

import com.ss.scrumptious_restaurant.client.AuthClient;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.UserRepository;
import com.ss.scrumptious_restaurant.dto.CreatRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.entity.User;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class RestaurantOwnerServiceImplTest {

  private final static UUID firstOwnerId = UUID.randomUUID();
  private final static UUID secondOwnerId = UUID.randomUUID();
  private static RestaurantOwner firstOwner;
  private static RestaurantOwner secondOwner;

  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final RestaurantOwnerRepository ownerRepository = Mockito.mock(RestaurantOwnerRepository.class);
  private final AuthClient authClient = Mockito.mock(AuthClient.class);
  private final RestaurantOwnerService service = new RestaurantOwnerServiceImpl(authClient, ownerRepository, userRepository);

  @BeforeEach
  void setUp() {
    firstOwner = RestaurantOwner.builder()
            .id(firstOwnerId)
            .firstName("John")
            .lastName("Smith")
            .email("john_smith@test.com")
            .phone("123-456-7890")
            .build();

    secondOwner = RestaurantOwner.builder()
            .id(secondOwnerId)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane_doe@test.com")
            .phone("987-654-3210")
            .build();
  }

  @AfterEach
  void tearDown() {}

  @Test
  void createNewRestaurantOwner() {
    when(ownerRepository.save(any(RestaurantOwner.class))).thenReturn(firstOwner);
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(authClient.createNewAccountRestaurantOwner(any())).thenReturn(ResponseEntity.ok(firstOwnerId));

    CreatRestaurantOwnerDto createDto = CreatRestaurantOwnerDto.builder()
            .firstName("John")
            .lastName("Smith")
            .password("123")
            .email("john_smith@test.com")
            .phone("123-456-7890")
            .build();

    UUID result = service.createNewRestaurantOwner(createDto);
    assertEquals(firstOwnerId, result);
  }

  @Test
  void getRestaurantOwnerById() {
    when(ownerRepository.findById(firstOwner.getId())).thenReturn(Optional.of(firstOwner));
    RestaurantOwner result = service.getRestaurantOwnerById(firstOwner.getId());
    assertEquals(firstOwner, result);

    assertThrows(NoSuchElementException.class,
            () -> service.getRestaurantOwnerById(null));
  }

  @Test
  void getRestaurantOwnerByEmail() {
    when(ownerRepository.findByEmail(firstOwner.getEmail())).thenReturn(Optional.of(firstOwner));
    RestaurantOwner result = service.getRestaurantOwnerByEmail(firstOwner.getEmail());
    assertEquals(firstOwner, result);

    assertThrows(NoSuchElementException.class,
            () -> service.getRestaurantOwnerByEmail(null));
  }

  @Test
  void updateRestaurantOwner() {

    when(ownerRepository.findById(firstOwner.getId()))
            .thenReturn(Optional.of(firstOwner));

    when(userRepository.findById(firstOwner.getId()))
            .thenReturn(Optional.of(User.builder().id(firstOwner.getId()).build()));

    UpdateRestaurantOwnerDto updateDto = UpdateRestaurantOwnerDto.builder()
            .firstName("John")
            .lastName("Smith")
            .email("john_smith@aol.com")
            .phone("123-456-7890")
            .build();
    service.updateRestaurantOwner(firstOwner.getId(), updateDto);
    Mockito.verify(ownerRepository).save(any());
    firstOwner.setEmail("john_smith@test.com");
  }

  @Test
  void updateRestaurantOwner_throwException_DuplicateUserEmail() {

    when(ownerRepository.findById(firstOwner.getId()))
            .thenReturn(Optional.of(firstOwner));

    when(userRepository.findByEmail("jane_doe@test.com"))
        .thenReturn(Optional.of(User.builder().id(UUID.randomUUID()).email("jane_doe@test.com").build()));

    UpdateRestaurantOwnerDto updateDto = UpdateRestaurantOwnerDto.builder()
            .firstName("John")
            .lastName("Smith")
            .email("jane_doe@test.com")
            .phone("123-456-7890")
            .build();

    assertThrows(IllegalArgumentException.class,
            () -> service.updateRestaurantOwner(firstOwner.getId(), updateDto));

  }

  @Test
  void updateRestaurantOwner_throwException_DuplicateOwnerEmail() {

    when(ownerRepository.findById(firstOwner.getId()))
            .thenReturn(Optional.of(firstOwner));

    when(ownerRepository.findByEmail(firstOwner.getEmail()))
            .thenReturn(Optional.of(firstOwner));

    when(ownerRepository.findByEmail(secondOwner.getEmail()))
            .thenReturn(Optional.of(secondOwner));

    UpdateRestaurantOwnerDto updateDto = UpdateRestaurantOwnerDto.builder()
            .firstName("John")
            .lastName("Smith")
            .email("jane_doe@test.com")
            .phone("123-456-7890")
            .build();

    assertThrows(IllegalArgumentException.class,
            () -> service.updateRestaurantOwner(firstOwner.getId(), updateDto));

  }


}
