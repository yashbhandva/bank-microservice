package com.easy_bank.account_service.service.impl;

import com.easy_bank.account_service.dto.*;
import com.easy_bank.account_service.exception.ResourceNotFoundException;
import com.easy_bank.account_service.model.Account;
import com.easy_bank.account_service.model.Customer;
import com.easy_bank.account_service.repository.AccountRepository;
import com.easy_bank.account_service.repository.CustomerRepository;
import com.easy_bank.account_service.service.CustomerDetailsService;
import com.easy_bank.account_service.service.client.CardsFeignClient;
import com.easy_bank.account_service.service.client.LoansFeignClient;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobile number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

        AccountDto accountRequestDto = AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .accountDto(accountRequestDto)
                .build();

        return  CustomerDetailDto
                .builder()
                .customerDto(customerDto)
                .loansDto(loansDtoResponseEntity.getBody())
                .cardsDto(cardsDtoResponseEntity.getBody())
                .build();
    }
}
