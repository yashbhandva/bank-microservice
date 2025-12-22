package com.easy_bank.account_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account extends BaseEntity{

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_number")
    @Id
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

}
