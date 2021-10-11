package com.ss.scrumptious_restaurant.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

	private final RestaurantOwnerService ownerService;
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
    public ResponseEntity<UUID> createRestaurantOwner(@Valid @RequestBody CreateRestaurantOwnerDto creatRestaurantOwnerDto) {
        UUID ownerId = ownerService.createNewRestaurantOwner(creatRestaurantOwnerDto);
        return ResponseEntity.of(Optional.ofNullable(ownerId));
    }

    @GetMapping("/{ownerId}")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerById(@PathVariable UUID ownerId) {
        RestaurantOwner owner = ownerService.getRestaurantOwnerById(ownerId);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @GetMapping("/email")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerEmailMatches(authentication, #email)")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerByEmail(@RequestParam String email) {
        RestaurantOwner owner = ownerService.getRestaurantOwnerByEmail(email);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @PutMapping("/{ownerId}")
	@PreAuthorize("hasRole('ADMIN')" + " OR @ownerAuthenticationManager.ownerIdMatches(authentication, #ownerId)")
    public ResponseEntity<Void> updateRestaurantOwner(@PathVariable UUID ownerId,
                                                      @Valid @RequestBody UpdateRestaurantOwnerDto updateDto) {
    	ownerService.updateRestaurantOwner(ownerId, updateDto);
        return ResponseEntity.noContent().build();
    }
}
