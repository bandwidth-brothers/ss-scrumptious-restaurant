package com.ss.scrumptious_restaurant.entity;

import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss.scrumptious_restaurant.dto.MonetaryAmountConverter;
import com.sun.istack.NotNull;

import lombok.*;

import java.util.Set;

@Entity
@Table(name="MENUITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	Restaurant restaurant;

	@NotBlank
	private String name;

	@NotNull
	@Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount price;

	@Builder.Default
	private Boolean isAvailable = false;

	private String picture;

	private String description;

	private String size;

	@Builder.Default
	private float discount = 0;

	@ManyToMany
	@EqualsAndHashCode.Exclude
	private Set<Tag> tags;

	@ManyToMany
	@EqualsAndHashCode.Exclude
	private Set<MenuCategory> categories;

	public NumberValue getPrice(){
		return price.getNumber();
	}
}
