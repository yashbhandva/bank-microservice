package com.easy_bank.account_service.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "card-service")
public interface CardsFeignClient {
}
