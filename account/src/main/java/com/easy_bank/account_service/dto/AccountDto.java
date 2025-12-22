package com.easy_bank.account_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @NotEmpty(message = "mobile number can not be a bull or empty!")
    @Pattern(regexp = "^$|[0-9]{10}" ,message = "mobile number must be contain 10 number")
    private Long accountNumber;

    @NotEmpty(message = "account type can not be a bull or empty!")
    private String accountType;

    @NotEmpty(message = "branch address  can not be a bull or empty!")
    private String branchAddress;
}
