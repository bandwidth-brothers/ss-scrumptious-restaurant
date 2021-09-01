package com.ss.scrumptious_restaurant.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ADDRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", name = "addressId", updatable = false)
    private UUID addressId;
	
	@OneToOne(mappedBy = "address")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
	private Restaurant restaurant;
	
	@NotBlank
    @Column(name="lineOne")
	private String lineOne;
	
	@Nullable
	@Column(name="lineTwo")
	private String lineTwo;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String zip;
}
