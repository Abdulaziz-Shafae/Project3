package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DtoIn.CusDtoIn;
import com.example.project3.DtoIn.CusEditIn;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/show/info")
    public ResponseEntity<?> getCusInfo(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(customerService.getCusInfo(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCus(@RequestBody @Valid CusDtoIn cusDtoIn){
        customerService.registerCus(cusDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered"));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editCus(@AuthenticationPrincipal User user , @RequestBody @Valid CusEditIn cusEditIn){
        customerService.editCus(user.getId(), cusEditIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCus(@AuthenticationPrincipal User user){
        customerService.deleteCus(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
    }

}
