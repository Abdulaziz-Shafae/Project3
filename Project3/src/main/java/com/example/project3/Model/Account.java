package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The account number cant be blank")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$",
            message = "Account number must follow XXXX-XXXX-XXXX-XXXX format")
    @Check(constraints = "account_number REGEXP '^[0-9]{4}(-[0-9]{4}){3}$'")
    @Column(name = "account_number", columnDefinition = "VARCHAR(19) unique not null")
    private String accountNumber;

    @NotNull(message = "The balance cant be null")
    @DecimalMin(value = "0.0", message = "Balance must be non-negative")
    @Digits(integer = 13, fraction = 2)
    @Check(constraints = "balance >= 0")
    @Column(columnDefinition = "DECIMAL(15,2) not null")
    private BigDecimal balance;

    @Column(columnDefinition = "BOOLEAN not null default false")
    private boolean isActive = false;

    @Column(columnDefinition = "BOOLEAN not null default false")
    private boolean blocked = false;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Customer customer;
}