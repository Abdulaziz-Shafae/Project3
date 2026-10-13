package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DtoIn.CusDtoIn;
import com.example.project3.DtoIn.CusEditIn;
import com.example.project3.DtoOut.CusInfo;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public CusInfo getCusInfo(Integer cusId){
        Customer customer= customerRepository.findCustomerById(cusId);

        if(customer==null){
            throw new ApiException("Customer not found");
        }

        if(!customer.getUser().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        CusInfo cusInfo= new CusInfo();

        cusInfo.setId(customer.getId());
        cusInfo.setUsername(customer.getUser().getUsername());
        cusInfo.setName(customer.getUser().getName());
        cusInfo.setEmail(customer.getUser().getEmail());
        cusInfo.setPhoneNumber(customer.getPhoneNumber());

        return cusInfo;
    }

    @Transactional
    public void registerCus(CusDtoIn cusDtoIn){

        User userCheckName = authRepository.findUserByUsername(cusDtoIn.getUsername());
        if(userCheckName != null){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(cusDtoIn.getEmail());
        if(userCheckEmail != null ){
            throw new ApiException("The email is taken");
        }

        User user = new User();
        user.setUsername(cusDtoIn.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(cusDtoIn.getPassword()));
        user.setName(cusDtoIn.getName());
        user.setEmail(cusDtoIn.getEmail());
        user.setRole("CUSTOMER");

        authRepository.save(user);

        Customer customer = new Customer();

        customer.setPhoneNumber(cusDtoIn.getPhoneNumber());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    @Transactional
    public void editCus(Integer cusId , CusEditIn cusEditIn){

        Customer customer = customerRepository.findCustomerById(cusId);
        if(customer == null){
            throw new ApiException("Customer not found");
        }

        if(!customer.getUser().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        User userCheckName = authRepository.findUserByUsername(cusEditIn.getUsername());
        if(userCheckName != null && !userCheckName.getId().equals(customer.getUser().getId())){
            throw new ApiException("The username is taken");
        }

        User userCheckEmail = authRepository.findUserByEmail(cusEditIn.getEmail());
        if(userCheckEmail != null && !userCheckEmail.getId().equals(customer.getUser().getId())){
            throw new ApiException("The email is taken");
        }

        customer.getUser().setUsername(cusEditIn.getUsername());
        customer.getUser().setName(cusEditIn.getName());
        customer.getUser().setEmail(cusEditIn.getEmail());
        customer.setPhoneNumber(cusEditIn.getPhoneNumber());

        customerRepository.save(customer);

    }


    public void deleteCus(Integer cusId){

        User user = authRepository.findUserById(cusId);
        if(user == null){
            throw new ApiException("Customer not found");
        }

        if(!user.getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        authRepository.delete(user);

    }
}
