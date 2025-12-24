package com.easy_bank.account_service.service.impl;

import com.easy_bank.account_service.constant.AccountConstants;
import com.easy_bank.account_service.dto.AccountDto;
import com.easy_bank.account_service.dto.CustomerDto;
import com.easy_bank.account_service.exception.CustomerAlreadyExistException;
import com.easy_bank.account_service.exception.ResourceNotFoundException;
import com.easy_bank.account_service.model.Account;
import com.easy_bank.account_service.model.Customer;
import com.easy_bank.account_service.repository.AccountRepository;
import com.easy_bank.account_service.repository.CustomerRepository;
import com.easy_bank.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto requestDto) {
        Customer customer = Customer.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .mobileNumber(requestDto.getMobileNumber())
                .build();

        Optional<Customer> optionalCustomer = customerRepository.
                findByMobileNumber(requestDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            log.info("customer with {} mobile number was already registered", requestDto.getMobileNumber());
            throw new CustomerAlreadyExistException("customer with this mobile number was already registered ,please choose another mobile number...");
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long accountNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountConstants.SAVING);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccountDetail(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobile number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );

        AccountDto accountRequestDto = AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();

        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .accountDto(accountRequestDto)
                .build();

    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {

        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto == null) {
            return false;
        }

        Account account = accountRepository
                .findByAccountNumber(accountDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account", "account number", accountDto.getAccountNumber().toString()
                ));

        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());

        accountRepository.save(account);

        Customer customer = customerRepository.findById(account.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer", "customer id", account.getCustomerId().toString()
                ));

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());

        customerRepository.save(customer);

        return true;
    }

    @Transactional
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobile number", mobileNumber)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}