package com.easy_bank.account_service.controller;

import com.easy_bank.account_service.dto.CustomerDetailDto;
import com.easy_bank.account_service.service.CustomerDetailsService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerDetailsService customerDetailsService;

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailDto> fetchCustomerDetails(
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}" ,message = "mobile number must be contain 10 number")
            String mobileNumber){

        CustomerDetailDto customerDetailDto = customerDetailsService.fetchCustomerDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetailDto);

    }
}
