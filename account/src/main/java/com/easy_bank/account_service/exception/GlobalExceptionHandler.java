package com.easy_bank.account_service.exception;

import com.easy_bank.account_service.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> GlobalException(Exception exception,
                                                            WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> CustomerAlreadyExistExceptionHandler(CustomerAlreadyExistException exception,
                                                                                 WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        webRequest.getDescription(false),
        HttpStatus.BAD_REQUEST,
        exception.getMessage(),
        LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception,
                                                                                 WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto ,HttpStatus.BAD_REQUEST);
    }

}
