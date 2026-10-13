package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DtoIn.EmpDtoIn;
import com.example.project3.DtoIn.EmpEditIn;
import com.example.project3.DtoIn.EmpWorkEditIn;
import com.example.project3.DtoOut.EmpInfo;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;


    public EmpInfo getEmpInfo(Integer empId){

        Employee employee = employeeRepository.findEmployeeById(empId);

        if(employee == null){
            throw new ApiException("Employee not found");
        }

        if(!employee.getUser().getId().equals(empId)){
            throw new ApiException("Unauthorized");
        }

        EmpInfo empInfo = new EmpInfo();

        empInfo.setId(employee.getId());
        empInfo.setUsername(employee.getUser().getUsername());
        empInfo.setName(employee.getUser().getName());
        empInfo.setEmail(employee.getUser().getEmail());
        empInfo.setPosition(employee.getPosition());
        empInfo.setSalary(employee.getSalary());

        return empInfo;
    }


    @Transactional
    public void registerEmp(EmpDtoIn empDtoIn){

        User userCheckName = authRepository.findUserByUsername(empDtoIn.getUsername());
        if(userCheckName != null){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(empDtoIn.getEmail());
        if(userCheckEmail != null){
            throw new ApiException("The email is taken");
        }

        User user = new User();
        user.setUsername(empDtoIn.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(empDtoIn.getPassword()));
        user.setName(empDtoIn.getName());
        user.setEmail(empDtoIn.getEmail());
        user.setRole("EMPLOYEE");

        authRepository.save(user);

        Employee employee = new Employee();

        employee.setPosition(empDtoIn.getPosition());
        employee.setSalary(empDtoIn.getSalary());
        employee.setUser(user);

        employeeRepository.save(employee);
    }


    @Transactional
    public void editEmp(Integer empId, EmpEditIn empEditIn){

        Employee employee = employeeRepository.findEmployeeById(empId);
        if(employee == null){
            throw new ApiException("Employee not found");
        }

        User userCheckName = authRepository.findUserByUsername(empEditIn.getUsername());
        if(userCheckName != null && !userCheckName.getId().equals(employee.getUser().getId())){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(empEditIn.getEmail());
        if(userCheckEmail != null && !userCheckEmail.getId().equals(employee.getUser().getId())){
            throw new ApiException("The email is taken");
        }

        employee.getUser().setUsername(empEditIn.getUsername());
        employee.getUser().setName(empEditIn.getName());
        employee.getUser().setEmail(empEditIn.getEmail());

        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmp(Integer empId){

        Employee employee = employeeRepository.findEmployeeById(empId);
        if(employee == null){
            throw new ApiException("Employee not found");
        }

        authRepository.delete(employee.getUser());
    }

    @Transactional
    public void editEmpWork(Integer empId, EmpWorkEditIn empWorkEditIn){

        Employee employee = employeeRepository.findEmployeeById(empId);
        if(employee == null){
            throw new ApiException("Employee not found");
        }

        employee.setPosition(empWorkEditIn.getPosition());
        employee.setSalary(empWorkEditIn.getSalary());

        employeeRepository.save(employee);
    }
}