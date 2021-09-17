package com.ss.scrumptious_restaurant.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantOwnerDto {

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

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",
//            message = "Phone number must be in the form ###-###-####.")
    private String phoneNumber;
}
