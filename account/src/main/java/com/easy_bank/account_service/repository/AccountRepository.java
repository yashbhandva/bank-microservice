package com.easy_bank.account_service.repository;

import com.easy_bank.account_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account ,Long> {
    Optional<Account> findByCustomerId(Long aLong);

    Optional<Account> findByAccountNumber(Long accountNumber);

    void deleteByCustomerId(Long customerId);
}
