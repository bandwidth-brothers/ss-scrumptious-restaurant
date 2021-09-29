package com.ss.scrumptious_restaurant.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    @Column( name = "id", updatable = false)
    private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	private Address address;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name="owner_id", referencedColumnName = "id")
	private RestaurantOwner owner;
	
    @NotBlank
    private String name;

    @Builder.Default
    private Float rating = 0.f;

    @Enumerated(EnumType.STRING)
    @Column(name="price_category")
    private PriceCategory priceCategory;

    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name="RESTAURANT_CUISINE")
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Cuisine> cuisines = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<MenuItem> menuItems = new HashSet<>();

	public void addRestaurantCuisine(Cuisine rC) {
		cuisines.add(rC);
	}

}
