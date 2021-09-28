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
@Table(name="MENUITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name="restaurant_id", referencedColumnName = "id")
	Restaurant restaurant;
	
	@NotBlank
	private String name;
	
	@Builder.Default
    private Float rating = 0.f;

	@NotNull
	@Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount price;
	
	@Builder.Default
	@Column(name="is_available")
	private Boolean isAvailable = false;
}
