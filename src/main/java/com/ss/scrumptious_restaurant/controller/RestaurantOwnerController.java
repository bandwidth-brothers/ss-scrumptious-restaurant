package com.ss.scrumptious_restaurant.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_restaurant.dto.CreateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.dto.UpdateRestaurantOwnerDto;
import com.ss.scrumptious_restaurant.entity.Cuisine;
import com.ss.scrumptious_restaurant.entity.MenuCategory;
import com.ss.scrumptious_restaurant.entity.MenuItem;
import com.ss.scrumptious_restaurant.entity.Restaurant;
import com.ss.scrumptious_restaurant.entity.RestaurantOwner;
import com.ss.scrumptious_restaurant.entity.Tag;
import com.ss.scrumptious_restaurant.security.TokenModel;
import com.ss.scrumptious_restaurant.security.TokenService;
import com.ss.scrumptious_restaurant.service.MenuService;
import com.ss.scrumptious_restaurant.service.RestaurantOwnerService;
import com.ss.scrumptious_restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RestaurantOwnerController {
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final TokenService tokenUtils;

    @PostMapping("/owner/register")
    public ResponseEntity<UUID> createRestaurantOwner(@Valid @RequestBody CreateRestaurantOwnerDto creatRestaurantOwnerDto) {
        System.out.println(creatRestaurantOwnerDto);
        UUID uid = restaurantOwnerService.createNewRestaurantOwner(creatRestaurantOwnerDto);
        return ResponseEntity.of(Optional.ofNullable(uid));
    }

    @GetMapping("/owner/{uid}")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerById(@PathVariable UUID uid) {
        TokenModel tokenModel = tokenUtils.parseToken();
        if (!tokenModel.getRole().equals("ADMIN") && !tokenModel.getUid().equals(uid)) {
            return ResponseEntity.status(403).body(null);
        }

        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerById(uid);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @GetMapping("/owner/email/{email}")
    public ResponseEntity<RestaurantOwner> getRestaurantOwnerByEmail(@PathVariable String email) {
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerByEmail(email);
        return ResponseEntity.of(Optional.ofNullable(owner));
    }

    @PutMapping("/owner/{uid}")
    public ResponseEntity<Void> updateRestaurantOwner(@PathVariable UUID uid,
                                                      @Valid @RequestBody UpdateRestaurantOwnerDto updateDto) {

        restaurantOwnerService.updateRestaurantOwner(uid, updateDto);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/owner/{uid}/restaurant")
    public ResponseEntity<Long> createRestaurant(@PathVariable UUID uid,
                                                 @Valid @RequestBody SaveRestaurantDto restaurantDto) {
        restaurantDto.setRestaurantOwnerId(uid);
        Long restaurantId = restaurantService.createRestaurant(restaurantDto);

        return ResponseEntity.of(Optional.ofNullable(restaurantId));
    }


    @GetMapping("/owner/{uid}/restaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@PathVariable UUID uid) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsByOwnerId_Owner(uid);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/owner/{uid}/restaurant/{rid}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable UUID uid, @PathVariable Long rid) {
        Restaurant restaurant = restaurantService.getRestaurantById_Owner(rid);
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/owner/{uid}/restaurant/{rid}")
    public ResponseEntity<Void> updateRestaurantById(@PathVariable UUID uid,
                                                           @PathVariable Long rid,
                                                           @Valid @RequestBody SaveRestaurantDto dto) {
        System.out.println("dto: " + dto);
        restaurantService.updateRestaurantByOwner_Owner(uid, rid, dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * json format array: ["bbq", "bar", "pizza"]
     * @param uid
     * @param rid
     * @param categoryList
     * @return
     */
    @PostMapping(value = "/owner/{uid}/restaurant/{rid}/category")
    public ResponseEntity<Set<Cuisine>> addRestaurantCategories(@PathVariable UUID uid,
                                                                @PathVariable Long rid,
                                                                @RequestBody List<String> categoryList) {
        Set<Cuisine> restaurantCategories = restaurantService
                .saveRestaurantCategories_Owner(categoryList, rid);

        return ResponseEntity.ok(restaurantCategories);
    }

    @PostMapping("/owner/{uid}/restaurant/{rid}/menu-items")
    public ResponseEntity<Long> addMenuItem(@Valid @RequestBody SaveMenuItemDto menuItemDto,
                                            @PathVariable Long rid,
                                            @PathVariable UUID uid) {
        Long id = menuService.addMenuItem_Owner(menuItemDto, rid);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/owner/{uid}/restaurant/menu-items/{mid}")
    public ResponseEntity<Void> updateMenuItem(@RequestBody SaveMenuItemDto dto,
                                                    @PathVariable Long mid,
                                                    @PathVariable UUID uid) {
        System.out.println("dto: " + dto);
        menuService.updateMenuItemById_Owner(dto, mid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{uid}/restaurant/menu-items/{mid}")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable UUID uid,
                                                @PathVariable Long mid) {
        MenuItem m = menuService.getMenuItemById_Owner(mid);
        return ResponseEntity.ok(m);
    }

    @GetMapping("/owner/{uid}/restaurant/{rid}/menu-items")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurantId(@PathVariable UUID uid,
                                                                     @PathVariable Long rid) {
        System.out.println(uid + " : " + rid);
        List<MenuItem> list = menuService.getMenuItemByRestaurantId_Owner(rid);
        return ResponseEntity.ok(list);

    }


    @PostMapping("/owner/{uid}/restaurant/menu-items/{mid}/tag")
    public ResponseEntity<List<Tag>> addMenuItemTag(@RequestBody List<String> tagList,
                                            @PathVariable Long mid,
                                            @PathVariable UUID uid) {
        List<Tag> tags = menuService.addMenuItemTag_Owner(tagList, mid);
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/owner/{uid}/restaurant/menu-items/{mid}/category")
    public ResponseEntity<List<MenuCategory>> addMenuItemCategory(@RequestBody List<String> categoryList,
                                                    @PathVariable Long mid,
                                                    @PathVariable UUID uid) {
        List<MenuCategory> categories = menuService.addMenuItemCategory_Owner(categoryList, mid);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/owner/{uid}/restaurant/menu-items")
    public ResponseEntity<Void> addMenuItemCategory(@RequestBody List<Long> ids,
                                                    @PathVariable UUID uid) {
        menuService.deleteMenuItemByIds_Owner(ids);
        return ResponseEntity.noContent().build();
    }

}
