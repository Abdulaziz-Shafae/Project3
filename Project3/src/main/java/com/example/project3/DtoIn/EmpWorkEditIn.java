package com.example.project3.DtoIn;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpWorkEditIn {

    @NotBlank(message = "The position cant be blank")
    @Size(min = 2, max = 20, message = "Position must be 2 - 20 characters")
    private String position;

    @NotNull(message = "The salary cant be null")
    @Min(value = 1, message = "Salary must be more than 0")
    private Integer salary;
}