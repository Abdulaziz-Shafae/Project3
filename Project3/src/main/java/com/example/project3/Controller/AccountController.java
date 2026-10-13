package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DtoIn.AccountDtoIn;
import com.example.project3.DtoIn.MoneyDtoIn;
import com.example.project3.DtoIn.TransferDtoIn;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/show/accounts")
    public ResponseEntity<?> getAccounts (@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getAccounts(user.getId()));
    }

    @PostMapping("/create/account")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid AccountDtoIn accountDtoIn){
        accountService.createAccount(user.getId(), accountDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Account created successfully"));
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }

    @GetMapping("/show/account/{accountId}")
    public ResponseEntity<?> getAccountInfo(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        return ResponseEntity.status(200).body(accountService.getAccountInfo(user.getId(), accountId));
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<?> depositMoney(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestBody @Valid MoneyDtoIn moneyDtoIn){
        accountService.depositMoney(user.getId(), accountId, moneyDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Money deposited successfully"));
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<?> withdrawMoney(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestBody @Valid MoneyDtoIn moneyDtoIn){
        accountService.withdrawMoney(user.getId(), accountId, moneyDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Money withdrawn successfully"));
    }

    @PostMapping("/transfer/{accountId}")
    public ResponseEntity<?> transferMoney(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestBody @Valid TransferDtoIn transferDtoIn){
        accountService.transferMoney(user.getId(), accountId, transferDtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("Money transferred successfully"));
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }

}
