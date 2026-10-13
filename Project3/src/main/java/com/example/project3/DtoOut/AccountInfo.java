package com.example.project3.DtoOut;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private Integer id;
    private String accountNumber;
    private BigDecimal balance;
    private boolean isActive;
    private boolean blocked;

}