package com.ss.scrumptious_restaurant.service.Impl;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.client.AuthClient;
import com.ss.scrumptious_restaurant.dao.AdminRepository;
import com.ss.scrumptious_restaurant.dto.AuthDto;
import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious.common_entities.entity.Admin;
import com.ss.scrumptious_restaurant.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AuthClient authClient;
	private AdminRepository adminRepository;

	@Override
	public Admin getAdminById(UUID adminId) {
		Admin admin = adminRepository.findById(adminId).orElseThrow(null);
		return admin;
	}


	public UUID createNewAdmin(@Valid CreateAdminDto adminDto) {
		AuthDto authDto = AuthDto.builder().email(adminDto.getEmail())
                .password(adminDto.getPassword()).build();
        ResponseEntity<UUID> resp = authClient.createNewAccountAdmin(authDto);
        if (resp.getBody() == null){
            throw new IllegalStateException("Email is already in use");
        }

		Admin admin = Admin.builder()
				.id(resp.getBody())
				.firstName(adminDto.getFirstName())
				.lastName(adminDto.getLastName())
				.email(adminDto.getEmail())
				.phone(adminDto.getPhone())
				.build();
			
		
		Admin adminRet = adminRepository.save(admin);
        return adminRet.getId();
	}
	

	public Admin getAdminDetails(@Valid UUID adminId) {

		Admin admin = adminRepository.findById(adminId).orElseThrow(null);
		return admin;
	}


}
