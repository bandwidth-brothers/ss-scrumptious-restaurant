package com.ss.scrumptious_restaurant.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ss.scrumptious.common_entities.entity.RestaurantOwner;
import com.ss.scrumptious.common_entities.entity.User;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.UserRepository;
import com.ss.scrumptious_restaurant.dto.AuthDto;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    @Override
    public UUID createNewRestaurantOwner(CreateRestaurantOwnerDto ownerDto) {
        AuthDto authDto = AuthDto.builder().email(ownerDto.getEmail())
                .password(ownerDto.getPassword()).build();
        String url = "http://application-load-balancer-1420109418.us-east-2.elb.amazonaws.com/auth/owner/register"; // Auth Microservice Endpoint
        ResponseEntity<UUID> resp = this.restTemplate.postForEntity(url, authDto, UUID.class);
        if (resp.getBody() == null) {
            throw new IllegalStateException("Email is already in use");
        }


        RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                .firstName(ownerDto.getFirstName())
                .lastName(ownerDto.getLastName())
                .email(ownerDto.getEmail())
                .phone(ownerDto.getPhone())
                .build();
        restaurantOwner.setId(resp.getBody());
        RestaurantOwner owner = restaurantOwnerRepository.save(restaurantOwner);


        return owner.getId();
    }

    @Override
    public RestaurantOwner getRestaurantOwnerById(UUID id) {
        RestaurantOwner owner = restaurantOwnerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found"));
        return owner;
    }

    @Override
    public RestaurantOwner getRestaurantOwnerByEmail(String email) {
        RestaurantOwner owner = restaurantOwnerRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("user not found"));
        return owner;
    }
    
    @Override
	public List<RestaurantOwner> getAllRestaurantOwners() {
		return restaurantOwnerRepository.findAll();
	}

    @Transactional
    @Override
    public void updateRestaurantOwner(UUID uid, UpdateRestaurantOwnerDto updateDto) {

        Optional<RestaurantOwner> owner = restaurantOwnerRepository.findById(uid);
        if (!owner.get().getEmail().equals(updateDto.getEmail())) {

            Optional<RestaurantOwner> existOwner = restaurantOwnerRepository.findByEmail(updateDto.getEmail());
            if (existOwner.isPresent() && !existOwner.get().getId().equals(uid)) {
                throw new IllegalArgumentException("Email is already in use");
            }

            Optional<User> existUser = userRepository.findByEmail(updateDto.getEmail());
            if (existUser.isPresent() && !existUser.get().getId().equals(uid)) {
                throw new IllegalArgumentException("Email is already in use");
            } else {
                User u = userRepository.findById(uid).get();
                u.setEmail(updateDto.getEmail());
                userRepository.save(u);
            }
        }

        RestaurantOwner newOwner = RestaurantOwner.builder().id(uid)
                .firstName(updateDto.getFirstName())
                .lastName(updateDto.getLastName())
                .email(updateDto.getEmail())
                .phone(updateDto.getPhone())
                .build();
        restaurantOwnerRepository.save(newOwner);
    }

	

	
}
