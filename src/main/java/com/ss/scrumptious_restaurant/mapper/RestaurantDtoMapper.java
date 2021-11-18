package com.ss.scrumptious_restaurant.mapper;

import com.ss.scrumptious_restaurant.dto.SaveRestaurantDto;
import com.ss.scrumptious_restaurant.entity.Address;
import com.ss.scrumptious_restaurant.entity.PriceCategory;
import com.ss.scrumptious_restaurant.entity.Restaurant;

public class RestaurantDtoMapper {

    public static Restaurant map(SaveRestaurantDto dto){
        return Restaurant.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .isActive(dto.getIsActive())
                .priceCategory(PriceCategory.valueOf(dto.getPriceCategory()))
                .address(Address.builder()
                        .line1(dto.getLineOne())
                        .line2(dto.getLineTwo())
                        .city(dto.getCity())
                        .state(dto.getState())
                        .zip(dto.getZip())
                        .build())
                .build();
    }
}
