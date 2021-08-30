package com.ss.scrumptious_restaurant.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RESTAURANT_CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "BINARY(16)", name = "restaurantCategoryId", updatable = false)
	private UUID restaurantCategoryId;
	
	@NotBlank
	@Column(unique = true)
	private String type;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "restaurantCategories")
	@EqualsAndHashCode.Exclude
	private Set<Restaurant> restaurants = new HashSet<>();
}
