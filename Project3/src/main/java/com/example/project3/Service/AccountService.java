package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DtoIn.AccountDtoIn;
import com.example.project3.DtoIn.MoneyDtoIn;
import com.example.project3.DtoIn.TransferDtoIn;
import com.example.project3.DtoOut.AccountInfo;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import com.example.project3.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public List<AccountInfo> getAccounts(Integer cusId){

        Customer customer = customerRepository.findCustomerById(cusId);

        if(customer == null){
            throw new ApiException("Customer not found");
        }

        List<Account> accounts = accountRepository.findAllByCustomer(customer);
        List<AccountInfo> accountsInfo = new ArrayList<>();

        for(Account account : accounts){

            AccountInfo accountInfo = new AccountInfo();

            accountInfo.setId(account.getId());
            accountInfo.setAccountNumber(account.getAccountNumber());
            accountInfo.setBalance(account.getBalance());
            accountInfo.setActive(account.isActive());
            accountInfo.setBlocked(account.isBlocked());

            accountsInfo.add(accountInfo);
        }

        return accountsInfo;
    }

    @Transactional
    public void createAccount(Integer cusId, AccountDtoIn accountDtoIn){

        Customer customer = customerRepository.findCustomerById(cusId);

        if(customer == null){
            throw new ApiException("Customer not found");
        }

        if(accountRepository.existsByAccountNumber(accountDtoIn.getAccountNumber())){
            throw new ApiException("Account number already exists");
        }

        Account account = new Account();

        account.setAccountNumber(accountDtoIn.getAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setActive(false);
        account.setCustomer(customer);

        accountRepository.save(account);
    }


    @Transactional
    public void activateAccount(Integer EmpId, Integer accountId){

        Employee employee = employeeRepository.findEmployeeById(EmpId);

        if(employee == null){
            throw new ApiException("Employee not found");
        }

        Account account = accountRepository.findAccountById(accountId);


        if(account == null){
            throw new ApiException("Account not found");
        }

        if(account.isBlocked()){
            throw new ApiException("Account is blocked");
        }


        if(account.isActive()){
            throw new ApiException("Account is already active");
        }

        account.setActive(true);

        accountRepository.save(account);
    }

    public AccountInfo getAccountInfo(Integer cusId, Integer accountId){

        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            throw new ApiException("Account not found");
        }

        if(!account.getCustomer().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        AccountInfo accountInfo = new AccountInfo();

        accountInfo.setId(account.getId());
        accountInfo.setAccountNumber(account.getAccountNumber());
        accountInfo.setBalance(account.getBalance());
        accountInfo.setActive(account.isActive());
        accountInfo.setBlocked(account.isBlocked());

        return accountInfo;
    }

    @Transactional
    public void depositMoney(Integer cusId, Integer accountId, MoneyDtoIn moneyDtoIn){

        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            throw new ApiException("Account not found");
        }

        if(!account.getCustomer().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        if(account.isBlocked()){
            throw new ApiException("Account is blocked");
        }

        if(!account.isActive()){
            throw new ApiException("Account is not active");
        }

        if(moneyDtoIn.getAmount() == null || moneyDtoIn.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new ApiException("Amount must be more than 0");
        }

        account.setBalance(account.getBalance().add(moneyDtoIn.getAmount()));

        accountRepository.save(account);
    }

    @Transactional
    public void withdrawMoney(Integer cusId, Integer accountId, MoneyDtoIn moneyDtoIn){

        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            throw new ApiException("Account not found");
        }

        if(!account.getCustomer().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        if(account.isBlocked()){
            throw new ApiException("Account is blocked");
        }

        if(!account.isActive()){
            throw new ApiException("Account is not active");
        }

        if(moneyDtoIn.getAmount() == null || moneyDtoIn.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new ApiException("Amount must be more than 0");
        }

        if(account.getBalance().compareTo(moneyDtoIn.getAmount()) < 0){
            throw new ApiException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(moneyDtoIn.getAmount()));

        accountRepository.save(account);
    }

    @Transactional
    public void transferMoney(Integer cusId, Integer fromAccountId, TransferDtoIn transferDtoIn){

        Account fromAccount = accountRepository.findAccountById(fromAccountId);

        if(fromAccount == null){
            throw new ApiException("Sender account not found");
        }

        if(!fromAccount.getCustomer().getId().equals(cusId)){
            throw new ApiException("Unauthorized");
        }

        if(fromAccount.isBlocked()){
            throw new ApiException("Account is blocked");
        }

        Account toAccount = accountRepository.findAccountByAccountNumber(transferDtoIn.getToAccountNumber());

        if(toAccount == null){
            throw new ApiException("Receiver account not found");
        }

        if(toAccount.isBlocked()){
            throw new ApiException("Account is blocked");
        }

        if(fromAccount.getId().equals(toAccount.getId())){
            throw new ApiException("Cannot transfer money to the same account");
        }

        if(!fromAccount.isActive() || !toAccount.isActive()){
            throw new ApiException("Both accounts must be active");
        }

        BigDecimal amount = transferDtoIn.getAmount();

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new ApiException("Amount must be more than 0");
        }

        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new ApiException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional
    public void blockAccount(Integer empId, Integer accountId){

        Employee employee = employeeRepository.findEmployeeById(empId);

        if(employee == null){
            throw new ApiException("Employee not found");
        }

        Account account = accountRepository.findAccountById(accountId);

        if(account == null){
            throw new ApiException("Account not found");
        }

        if(account.isBlocked()){
            throw new ApiException("Account is already blocked");
        }

        account.setBlocked(true);

        accountRepository.save(account);
    }
}
