package com.easy_bank.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private String apiPath;

    private HttpStatus statusCode;

    private String errorMsg;

    private LocalDateTime errorTime;
}
