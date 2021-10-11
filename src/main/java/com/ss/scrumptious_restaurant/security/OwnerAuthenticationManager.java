package com.ss.scrumptious_restaurant.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerAuthenticationManager {

	private RestaurantRepository restaurantRepository;
	
	public boolean ownerIdMatches(Authentication authentication, SaveRestaurantDto restaurantDto) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return principal.getUserId().equals(restaurantDto.getRestaurantOwnerId());
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	public boolean ownerIdMatches(Authentication authentication, UUID ownerId) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return principal.getUserId().equals(ownerId);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	public boolean ownerIdMatches(Authentication authentication, Long restaurantId) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return restaurantRepository.findById(restaurantId)
					.map(r -> principal.getUserId().equals(r.getOwner().getId()))
					.orElse(false);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	
	public boolean ownerEmailMatches(Authentication authentication, String email) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return principal.getUsername().equals(email);
		} catch (ClassCastException ex) {
			return false;
		}
	}
}
