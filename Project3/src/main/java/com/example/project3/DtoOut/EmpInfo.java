package com.example.project3.DtoOut;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpInfo {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private String position;
    private Integer salary;

}