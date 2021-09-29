package com.ss.scrumptious_restaurant.dto;

import lombok.Data;

import java.util.List;
@Data
public class SaveMenuItemDto {
    private String name;
    private Float price;
    private Boolean isAvailable;
    private String description;
    private String size;
    private Float discount;
    private List<String> categories;
    private List<String> tags;
}
