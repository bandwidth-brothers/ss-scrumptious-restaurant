package com.ss.scrumptious_restaurant.dto;


import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatRestaurantOwnerDto {

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
    @Size(min = 3, max = 10, message = "Length must be between 3 and 10 characters.")
    private String password;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",
//            message = "Phone number must be in the form ###-###-####.")
    private String phoneNumber;
}
