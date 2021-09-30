package com.ss.scrumptious_restaurant.dto;

import lombok.*;

import java.util.List;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
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
