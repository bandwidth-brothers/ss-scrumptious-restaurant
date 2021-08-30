package com.ss.scrumptious_restaurant.entity;

import java.util.UUID;

import javax.money.MonetaryAmount;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.ss.scrumptious_restaurant.dto.MonetaryAmountConverter;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MENU_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "BINARY(16)", name = "menuItemId", updatable = false)
	private UUID menuItemId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name="restaurantId", referencedColumnName = "restaurantId")
	Restaurant restaurant;
	
	@NotBlank
	private String name;
	
	@Builder.Default
    private Float rating = 0.f;

	@NotNull
	@Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount price;
	
	@Builder.Default
	private Boolean isAvailable = false;
}
