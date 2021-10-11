package com.ss.scrumptious_restaurant.service;

import java.util.UUID;

import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious_restaurant.entity.Admin;

public interface AdminService {

	Admin getAdminDetails(UUID adminId);
	UUID createNewAdmin(CreateAdminDto createAdminDto);
	
}
