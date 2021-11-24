package com.ss.scrumptious_restaurant.service;

import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.Admin;
import com.ss.scrumptious_restaurant.dto.CreateAdminDto;

public interface AdminService {

	Admin getAdminById(UUID adminId);
	UUID createNewAdmin(CreateAdminDto createAdminDto);

}
