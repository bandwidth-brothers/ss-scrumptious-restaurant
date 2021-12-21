package com.ss.scrumptious_restaurant.client;


import com.ss.scrumptious_restaurant.dto.AuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@FeignClient(name = "auth-service", url = "${base.url}")
public interface AuthClient {

    @PostMapping("/auth/owner/register")
    ResponseEntity<UUID> createNewAccountRestaurantOwner(@Valid @RequestBody AuthDto authDto);

    @PostMapping("/auth/admin/register")
    ResponseEntity<UUID> createNewAccountAdmin(@Valid @RequestBody AuthDto authDto);
}
