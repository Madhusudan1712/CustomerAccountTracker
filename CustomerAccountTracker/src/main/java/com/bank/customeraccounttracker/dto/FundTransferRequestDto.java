package com.bank.customeraccounttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundTransferRequestDto {
    private Long fromAccount;
    private Long toAccount;
    private double amount;
}
