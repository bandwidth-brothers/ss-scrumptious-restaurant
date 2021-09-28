package com.ss.scrumptious_restaurant.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.client.AuthClient;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.UserRepository;
import com.ss.scrumptious_restaurant.dto.AuthDto;
import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {
    private final AuthClient authClient;
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final UserRepository userRepository;


    @Override
    public UUID createNewRestaurantOwner(CreateRestaurantOwnerDto ownerDto) {
        AuthDto authDto = AuthDto.builder().email(ownerDto.getEmail())
                .password(ownerDto.getPassword()).build();
        ResponseEntity<UUID> resp = authClient.createNewAccountRestaurantOwner(authDto);
        if (resp.getBody() == null){
            throw new IllegalStateException("Email is already in use");
        }

        System.out.println("cline id: "  + resp.getBody());

        RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                .firstName(ownerDto.getFirstName())
                .lastName(ownerDto.getLastName())
                .email(ownerDto.getEmail())
                .phone(ownerDto.getPhone())
                .build();
        restaurantOwner.setId(resp.getBody());
        RestaurantOwner owner = restaurantOwnerRepository.save(restaurantOwner);

        System.out.println("owner id: " + owner.getId());

        return owner.getId();
    }

    @Override
    public RestaurantOwner getRestaurantOwnerById(UUID id) {
        RestaurantOwner owner = restaurantOwnerRepository.findById(id).orElseThrow(()-> new IllegalStateException("user not found"));
        return owner;
    }

    @Override
    public RestaurantOwner getRestaurantOwnerByEmail(String email) {
        RestaurantOwner owner = restaurantOwnerRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException("user not found"));
        return owner;
    }

    @Transactional
    @Override
    public void updateRestaurantOwner(UUID uid, CreateRestaurantOwnerDto updateDto) {

        Optional<RestaurantOwner> owner = restaurantOwnerRepository.findById(uid);
        if (!owner.get().getEmail().equals(updateDto.getEmail())){

            Optional<RestaurantOwner> existOwner = restaurantOwnerRepository.findByEmail(updateDto.getEmail());
            if (existOwner.isPresent() && !existOwner.get().getId().equals(uid)){
                throw new IllegalStateException("Email is already in use");
            }

            Optional<User> existUser = userRepository.findByEmail(updateDto.getEmail());
            if (existUser.isPresent() && !existUser.get().getId().equals(uid)){
                throw new IllegalStateException("Email is already in use");
            }
        }

        RestaurantOwner newOwner = RestaurantOwner.builder()
        		.id(uid)
                .firstName(updateDto.getFirstName())
                .lastName(updateDto.getLastName())
                .email(updateDto.getEmail())
                .phone(updateDto.getPhone())
                .build();
        restaurantOwnerRepository.save(newOwner);
    }


}