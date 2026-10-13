package com.example.project3.DtoIn;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpEditIn {

    @NotBlank(message = "The username cant be blank")
    @Size(min = 4, max = 10, message = "Username must be 4 - 10 characters")
    private String username;

    @NotBlank(message = "The name cant be blank")
    @Size(min = 2, max = 20, message = "Name must be 2 - 20 characters")
    private String name;

    @NotBlank(message = "The email cant be blank")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;
}