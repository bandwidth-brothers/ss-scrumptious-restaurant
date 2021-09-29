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
                .priceCategory(PriceCategory.valueOf(dto.getPriceCategory()))
                .address(Address.builder()
                        .lineOne(dto.getLineOne())
                        .lineTwo(dto.getLineTwo())
                        .city(dto.getCity())
                        .state(dto.getState())
                        .zip(dto.getZip())
                        .build())
                .build();
    }
}
