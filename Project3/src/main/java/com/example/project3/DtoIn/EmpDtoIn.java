package com.example.project3.DtoIn;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpDtoIn {

    @NotBlank(message = "The username cant be blank")
    @Size(min = 4 ,max = 10, message = "Username must be 4 - 10 characters")
    private String username;


    @NotBlank(message = "The password cant be blank")
    @Size(min = 6, message = "password must be more than 6 characters")
    private String password;

    @NotBlank(message = "The name cant be blank")
    @Size(min = 2 ,max = 20, message = "Name must be 2 - 20 characters")
    private String name;

    @NotBlank(message = "The email cant be blank")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "The position cant be blank")
    @Size(min = 2 ,max = 20, message = "Position must be 2 - 20 characters")
    private String position;

    @NotNull(message = "The salary cant be null")
    @Min(value = 1 , message = "Salary must be more than 0")
    private Integer salary;

}
