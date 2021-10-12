package com.ss.scrumptious_restaurant.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRestaurantOwnerDto {

    @NotNull
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be valid.")
    private String email;

    @ToString.Exclude
    @NotNull
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 3, message = "Length must be between 3 and 10 characters.")
    private String password;

    @NotNull
    @NotBlank
    private String phone;
}
