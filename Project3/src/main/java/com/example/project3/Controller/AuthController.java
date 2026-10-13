package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DtoIn.AdminDtoIn;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFirstAdmin(@RequestBody @Valid AdminDtoIn adminDtoIn) {
        authService.registerFirstAdmin(adminDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("user registered"));
    }

    @PostMapping("/admin/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid AdminDtoIn adminDtoIn) {
        authService.registerAdmin(adminDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("admin registered"));
    }

    @GetMapping("/show/users")
    public ResponseEntity<?> adminGetAllUsers(@AuthenticationPrincipal User admin) {
        return ResponseEntity.status(200).body(authService.adminGetAllUsers(admin.getId()));
    }

}
