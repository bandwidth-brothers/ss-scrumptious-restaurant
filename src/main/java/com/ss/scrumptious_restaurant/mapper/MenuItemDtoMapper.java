package com.ss.scrumptious_restaurant.mapper;

import org.javamoney.moneta.Money;

import com.ss.scrumptious_restaurant.dto.SaveMenuItemDto;
import com.ss.scrumptious_restaurant.entity.MenuItem;

public class MenuItemDtoMapper {

    public static MenuItem map(SaveMenuItemDto dto){
        return MenuItem.builder()
                .name(dto.getName())
                .isAvailable(dto.getIsAvailable())
                .price(Money.of(dto.getPrice(), "USD"))
                .discount(dto.getDiscount())
                .description(dto.getDescription())
                .size(dto.getSize())
                .build();

    }
}
