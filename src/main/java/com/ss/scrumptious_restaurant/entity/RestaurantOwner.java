package com.ss.scrumptious_restaurant.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESTAURANT_OWNER")
@Builder
public class RestaurantOwner {

	@Id
	@Column(columnDefinition = "BINARY(16)", name = "restaurantOwnerId")
	private UUID restaurantOwnerId;


	@NotBlank
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@NotBlank
	@Column(name = "lastName", nullable = false)
	private String lastName;

	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;

	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;


}
