package com.ss.scrumptious_restaurant.entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	private Address address;

    @NotBlank
    private String name;

    @Builder.Default
    private Float rating = 0.f;

    private String phone;

    private String logo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PriceCategory priceCategory = PriceCategory.$;

    @Builder.Default
    private Boolean isActive = true;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private Set<Cuisine> cuisines;

    @JsonIgnore
    @ManyToOne
    private RestaurantOwner owner;

	public void addRestaurantCuisine(Cuisine rC) {

		cuisines.add(rC);
	}

}
