package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DtoIn.AdminDtoIn;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void registerFirstAdmin(AdminDtoIn adminDtoIn) {

        if(authRepository.existsByRole("ADMIN")){
            throw new ApiException("The first admin already exists");
        }

        User user = authRepository.findUserByUsername(adminDtoIn.getUsername());
        if(user != null){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(adminDtoIn.getEmail());

        if(userCheckEmail != null){
            throw new ApiException("The email is taken");
        }

        User newUser = new User();
        newUser.setRole("ADMIN");

        newUser.setUsername(adminDtoIn.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(adminDtoIn.getPassword()));
        newUser.setName(adminDtoIn.getName());
        newUser.setEmail(adminDtoIn.getEmail());

        authRepository.save(newUser);
    }

    public void registerAdmin(AdminDtoIn adminDtoIn) {

        User user = authRepository.findUserByUsername(adminDtoIn.getUsername());
        if(user != null){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(adminDtoIn.getEmail());

        if(userCheckEmail != null){
            throw new ApiException("The email is taken");
        }

        User newUser = new User();
        newUser.setRole("ADMIN");

        newUser.setUsername(adminDtoIn.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(adminDtoIn.getPassword()));
        newUser.setName(adminDtoIn.getName());
        newUser.setEmail(adminDtoIn.getEmail());

        authRepository.save(newUser);
    }


    public List<User> adminGetAllUsers(Integer userId){

        User userAdmin = authRepository.findUserById(userId);
        if(userAdmin==null){
            throw new ApiException("user not found");
        }

        if (!"ADMIN".equalsIgnoreCase(userAdmin.getRole())) {
            throw new ApiException("you dont have access");
        }

        return authRepository.findAll();
    }

}

