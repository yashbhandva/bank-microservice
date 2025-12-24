package com.easy_bank.account_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    @NotEmpty(message = "customer name can not be a null or empty!")
    private String name;

    @Email(message = "email address can not be a null or empty!")
    private String email;

    @NotEmpty(message = "mobile number can not be a bull or empty!")
    @Pattern(regexp = "^$|[0-9]{10}" ,message = "mobile number must be contain 10 number")
    private String mobileNumber;

    private AccountDto accountDto;
}
