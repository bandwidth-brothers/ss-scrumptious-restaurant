package com.ss.scrumptious_restaurant.service.Impl;

import com.ss.scrumptious_restaurant.client.AuthClient;
import com.ss.scrumptious_restaurant.dao.RestaurantOwnerRepository;
import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dao.UserRepository;
import com.ss.scrumptious_restaurant.dto.AuthDto;
import com.ss.scrumptious_restaurant.dto.CreatRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.entity.User;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {
    private final AuthClient authClient;
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final UserRepository userRepository;


    @Override
    public UUID createNewRestaurantOwner(CreatRestaurantOwnerDto ownerDto) {
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
                .phone(ownerDto.getPhoneNumber())
                .build();
        restaurantOwner.setRestaurantOwnerId(resp.getBody());
        RestaurantOwner owner = restaurantOwnerRepository.save(restaurantOwner);

        System.out.println("owner id: " + owner.getRestaurantOwnerId());

        return owner.getRestaurantOwnerId();
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
    public void updateRestaurantOwner(UUID uid, UpdateRestaurantOwnerDto updateDto) {

        Optional<RestaurantOwner> owner = restaurantOwnerRepository.findById(uid);
        if (!owner.get().getEmail().equals(updateDto.getEmail())){

            Optional<RestaurantOwner> existOwner = restaurantOwnerRepository.findByEmail(updateDto.getEmail());
            if (existOwner.isPresent() && !existOwner.get().getRestaurantOwnerId().equals(uid)){
                throw new IllegalStateException("Email is already in use");
            }

            Optional<User> existUser = userRepository.findByEmail(updateDto.getEmail());
            if (existUser.isPresent() && !existUser.get().getUserId().equals(uid)){
                throw new IllegalStateException("Email is already in use");
            }
        }

        RestaurantOwner newOwner = RestaurantOwner.builder().restaurantOwnerId(uid)
                .firstName(updateDto.getFirstName())
                .lastName(updateDto.getLastName())
                .email(updateDto.getEmail())
                .phone(updateDto.getPhoneNumber())
                .build();
        restaurantOwnerRepository.save(newOwner);
    }


}
