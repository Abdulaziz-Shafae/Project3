package com.example.project3.DtoIn;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDtoIn {

    @NotBlank(message = "The account number cant be blank")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$", message = "Account number must follow XXXX-XXXX-XXXX-XXXX format")
    private String toAccountNumber;

    @NotNull(message = "The amount cant be null")
    @DecimalMin(value = "0.01", message = "Amount must be more than 0")
    @Digits(integer = 13, fraction = 2, message = "Amount must have at most 2 decimal places")
    private BigDecimal amount;
}