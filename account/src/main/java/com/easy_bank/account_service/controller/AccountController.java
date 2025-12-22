package com.easy_bank.account_service.controller;

import com.easy_bank.account_service.constant.AccountConstants;
import com.easy_bank.account_service.dto.CustomerDto;
import com.easy_bank.account_service.dto.ResponseDto;
import com.easy_bank.account_service.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/api/account" , produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public ResponseEntity<CustomerDto> fetchCustomerDetails(
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}" ,message = "mobile number must be contain 10 number")
            String mobileNumber){

        CustomerDto customerDto = service.fetchAccountDetail(mobileNumber);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(customerDto);
    }
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto request){
        service.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201 ,AccountConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdate = service.updateAccount(customerDto);

        if (isUpdate){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200 ,AccountConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500 ,AccountConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}" ,message = "mobile number must be contain 10 number")
            String mobileNumber){

        boolean isDelete = service.deleteAccount(mobileNumber);

        if (isDelete){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200 ,AccountConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500 ,AccountConstants.MESSAGE_500));
        }
    }
}
