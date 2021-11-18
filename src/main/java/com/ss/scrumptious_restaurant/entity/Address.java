package com.ss.scrumptious_restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="ADDRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
	private String line1;

	@Nullable
	private String line2;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private String zip;
}
