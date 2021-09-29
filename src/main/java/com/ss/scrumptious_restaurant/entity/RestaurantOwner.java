package com.ss.scrumptious_restaurant.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OWNER")
@Builder
public class RestaurantOwner {

	@Id
	@Column(columnDefinition = "BINARY(16)", name = "id")
	private UUID id;
	
	
	@NotBlank
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToMany(mappedBy="owner")
	@JsonIgnore
	@EqualsAndHashCode.Exclude
    @Builder.Default
	private Set<Restaurant> restaurants = new HashSet<>();
}
