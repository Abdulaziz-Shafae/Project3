package com.example.project3.DtoOut;

import com.example.project3.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CusInfo {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;

}