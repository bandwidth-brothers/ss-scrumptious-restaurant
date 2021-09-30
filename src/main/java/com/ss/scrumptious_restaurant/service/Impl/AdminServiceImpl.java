package com.ss.scrumptious_restaurant.service.Impl;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_restaurant.client.AuthClient;
import com.ss.scrumptious_restaurant.dao.AdminRepository;
import com.ss.scrumptious_restaurant.dto.AuthDto;
import com.ss.scrumptious_restaurant.dto.CreateAdminDto;
import com.ss.scrumptious_restaurant.dto.UserIdDto;
import com.ss.scrumptious_restaurant.entity.Admin;
import com.ss.scrumptious_restaurant.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AuthClient authClient;
	private AdminRepository adminRepository;
	

	public UUID createNewAdmin(@Valid CreateAdminDto adminDto) {
		AuthDto authDto = AuthDto.builder().email(adminDto.getEmail())
                .password(adminDto.getPassword()).build();
        ResponseEntity<UUID> resp = authClient.createNewAccountAdmin(authDto);
        if (resp.getBody() == null){
            throw new IllegalStateException("Email is already in use");
        }

        System.out.println("cline id: "  + resp.getBody());
		Admin admin = Admin.builder()
				.id(resp.getBody())
				.firstName(adminDto.getFirstName())
				.lastName(adminDto.getLastName())
				.email(adminDto.getEmail())
				.phone(adminDto.getPhone())
				.build();
			
		
		Admin adminRet = adminRepository.save(admin);

        System.out.println("owner id: " + adminRet.getId());

        return adminRet.getId();
	}
	
	// TODO GET DETAILS
	public Admin getAdminDetails(@Valid UserIdDto userIdDto) {
		Admin admin = adminRepository.findById(userIdDto.getId()).orElseThrow();
		return admin;
	}
	
}
