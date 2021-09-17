package com.ss.scrumptious_restaurant.controller;

import com.ss.scrumptious_restaurant.dto.CreatRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.security.TokenModel;
import com.ss.scrumptious_restaurant.security.TokenService;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantOwnerService restaurantOwnerService;

    private final TokenService tokenUtils;

    @PostMapping("/owner/register")
    public ResponseEntity<UUID> createRestaurantOwner(@Valid @RequestBody CreatRestaurantOwnerDto creatRestaurantOwnerDto){
        System.out.println(creatRestaurantOwnerDto);
        UUID uid =  restaurantOwnerService.createNewRestaurantOwner(creatRestaurantOwnerDto);
        return ResponseEntity.of(Optional.ofNullable(uid));
    }

    @GetMapping("/owner/{uid}")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerById(@PathVariable UUID uid){
        TokenModel tokenModel = tokenUtils.parseToken();
        if (!tokenModel.getRole().equals("ADMIN") && !tokenModel.getUid().equals(uid)){
            return ResponseEntity.status(403).body(null);
        }

        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerById(uid);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @GetMapping("/owner/email/{email}")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerByEmail(@PathVariable String email){
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerByEmail(email);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @PutMapping("/owner/{uid}")
    public ResponseEntity<Void> updateRestaurantOwner(@PathVariable UUID uid,
                                                      @Valid @RequestBody
                                                              UpdateRestaurantOwnerDto updateDto){

        restaurantOwnerService.updateRestaurantOwner(uid, updateDto);
        return ResponseEntity.noContent().build();
    }



//    @GetMapping("/test")
//    public ResponseEntity<String> test(){
////        RestaurantOwner owner =  restaurantOwnerService.createNewRestaurantOwner(creatRestaurantOwnerDto);
//        return ResponseEntity.ok("456");
//    }
}
