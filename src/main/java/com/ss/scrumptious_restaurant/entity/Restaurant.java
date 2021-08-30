package com.ss.scrumptious_restaurant.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RESTAURANT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", name = "restaurantId", updatable = false)
    private UUID restaurantId;

	@OneToOne
	@JoinColumn(name="addressId", referencedColumnName = "addressId")
	@EqualsAndHashCode.Exclude
	private Address address;

    @NotBlank
    private String name;

    @Builder.Default
    private Float rating = 0.f;
    
    @Enumerated(EnumType.STRING)
    @Column(name="priceCategory")
    private PriceCategory priceCategory;

    @Builder.Default
    @Column(name="isActive")
    private Boolean isActive = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name="RESTAURANT_CATEGORY_REL", 
    		joinColumns = @JoinColumn(name = "restaurantId"),
    		inverseJoinColumns = @JoinColumn(name = "restaurantCategoryId"))
    @EqualsAndHashCode.Exclude
    private Set<RestaurantCategory> restaurantCategories = new HashSet<>();

	public void addRestaurantCategory(RestaurantCategory rC) {
		restaurantCategories.add(rC);
	}

}
