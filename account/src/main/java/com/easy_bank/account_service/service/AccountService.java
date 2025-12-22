package com.easy_bank.account_service.service;

import com.easy_bank.account_service.dto.CustomerDto;

public interface AccountService {
    void createAccount(CustomerDto requestDto);

    CustomerDto fetchAccountDetail(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
