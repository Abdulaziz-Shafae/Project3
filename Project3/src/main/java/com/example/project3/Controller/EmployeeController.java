package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DtoIn.EmpDtoIn;
import com.example.project3.DtoIn.EmpEditIn;
import com.example.project3.DtoIn.EmpWorkEditIn;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/show/info")
    public ResponseEntity<?> getEmpInfo(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(employeeService.getEmpInfo(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmp(@RequestBody @Valid EmpDtoIn empDtoIn){
        employeeService.registerEmp(empDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee registered"));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editEmp(@AuthenticationPrincipal User user, @RequestBody @Valid EmpEditIn empEditIn){
        employeeService.editEmp(user.getId(), empEditIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
    }

    @PutMapping("/admin/edit/{empId}")
    public ResponseEntity<?> editEmpWork(@PathVariable Integer empId, @RequestBody @Valid EmpWorkEditIn empWorkEditIn){
        employeeService.editEmpWork(empId, empWorkEditIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee work info updated"));
    }

    @DeleteMapping("/admin/delete/{empId}")
    public ResponseEntity<?> deleteEmp(@PathVariable Integer empId){
        employeeService.deleteEmp(empId);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
    }
}