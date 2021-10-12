package com.ss.scrumptious_restaurant.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ss.scrumptious_restaurant.dao.RestaurantRepository;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminAuthenticationManager {

	private RestaurantRepository restaurantRepository;
	
	public boolean adminIdMatches(Authentication authentication, UUID adminId) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return principal.getUserId().equals(adminId);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	
	public boolean adminEmailMatches(Authentication authentication, String email) {
		try {
			JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
			return principal.getUsername().equals(email);
		} catch (ClassCastException ex) {
			return false;
		}
	}
}
