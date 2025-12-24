package com.easy_bank.account_service.service.client;

import com.easy_bank.account_service.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-service")
public interface CardsFeignClient {

    @GetMapping("/api/fetch")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
