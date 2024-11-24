package com.bank.customeraccounttracker.dto;

import com.bank.customeraccounttracker.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	
    //private Long accountNumber; 
    private AccountType accountType; 
    private Double balance; 
    private Long customerId; // Customer ID (to reference the customer without exposing the full entity)
}
