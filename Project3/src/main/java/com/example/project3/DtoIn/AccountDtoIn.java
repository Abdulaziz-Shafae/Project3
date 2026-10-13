package com.example.project3.DtoIn;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoIn {

    @NotBlank(message = "The account number cant be blank")
    @Pattern(
            regexp = "^[0-9]{4}(-[0-9]{4}){3}$",
            message = "Account number must follow XXXX-XXXX-XXXX-XXXX format"
    )
    private String accountNumber;
}