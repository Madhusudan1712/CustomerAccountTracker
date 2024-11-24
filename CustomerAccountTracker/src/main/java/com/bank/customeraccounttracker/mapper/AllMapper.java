package com.bank.customeraccounttracker.mapper;

import com.bank.customeraccounttracker.dto.AccountDto;
import com.bank.customeraccounttracker.dto.CustomerDto;
import com.bank.customeraccounttracker.model.AccountModel;
import com.bank.customeraccounttracker.model.AccountType;
import com.bank.customeraccounttracker.model.CustomerModel;

public class AllMapper {
    public static CustomerDto toCustomerDto(CustomerModel customer) {
        CustomerDto dto = new CustomerDto();
        //dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setAadhaarNumber(customer.getAadhaarNumber());
        dto.setMobileNum(customer.getMobileNum());
        dto.setEmailId(customer.getEmailId());
        dto.setBankName(customer.getBankName());
        dto.setAddress(customer.getAddress());
        //dto.setCreatedAt(customer.getCreatedAt());
        
        // Map accounts if present in DTO
        if (customer.getAccounts() != null && !customer.getAccounts().isEmpty()) {
            dto.setAccounts(customer.getAccounts().stream().map(AllMapper::toAccountDto).toList());
        }
        return dto;
    }

    public static CustomerModel toCustomerEntity(CustomerDto dto) {
        CustomerModel customer = new CustomerModel();
        customer.setName(dto.getName());
        customer.setAadhaarNumber(dto.getAadhaarNumber());
        customer.setMobileNum(dto.getMobileNum());
        customer.setEmailId(dto.getEmailId());
        customer.setBankName(dto.getBankName());
        customer.setAddress(dto.getAddress());
        return customer;
    }
    
    public static AccountDto toAccountDto(AccountModel account) {
        AccountDto dto = new AccountDto();
        //dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCustomerId(account.getCustomer().getCustomerId()); // Set customerId
        return dto;
    }
    
    public static AccountModel toAccountEntity(AccountDto dto) {
        AccountModel account = new AccountModel();
        //account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        // Don't set the customer here, as it should be handled in the service layer
        return account;
    }
}

