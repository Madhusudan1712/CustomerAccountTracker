package com.bank.customeraccounttracker.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	
    private Long transactionId;
    private String name;
    private Long accountNumber;
    private Date transactionDate;
    private String transactionType;
    private double transactionAmount;
    private String fromOrToName;
    private Long fromOrToAccountNumber;
    private double totalBalance;
}
