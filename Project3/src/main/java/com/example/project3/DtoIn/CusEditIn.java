package com.example.project3.DtoIn;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CusEditIn {

    @NotBlank(message = "The username cant be blank")
    @Size(min = 4 ,max = 10, message = "Username must be 4 - 10 characters")
    private String username;


    @NotBlank(message = "The name cant be blank")
    @Size(min = 2 ,max = 20, message = "Name must be 2 - 20 characters")
    private String name;

    @NotBlank(message = "The email cant be blank")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "The phoneNumber cant be blank")
    @Pattern(regexp = "^05[0-9]{8}$", message = "Phone number must start with 05 and contain 10 digits")
    private String phoneNumber;

}
