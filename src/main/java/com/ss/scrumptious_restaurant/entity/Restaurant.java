package com.ss.scrumptious_restaurant.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
