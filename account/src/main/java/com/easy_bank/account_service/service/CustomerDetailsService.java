package com.easy_bank.account_service.service;

import com.easy_bank.account_service.dto.CustomerDetailDto;

public interface CustomerDetailsService {
    CustomerDetailDto fetchCustomerDetails(String mobileNumber);
}
