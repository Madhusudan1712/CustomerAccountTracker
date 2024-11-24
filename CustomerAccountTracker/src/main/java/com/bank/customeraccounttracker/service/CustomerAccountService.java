package com.bank.customeraccounttracker.service;

import com.bank.customeraccounttracker.dto.AccountDto;
import com.bank.customeraccounttracker.dto.CustomerDto;

public interface CustomerAccountService {
	String createAccount(CustomerDto dto);
	public CustomerDto getCustomerDetailsById(Long customerId);
	public String updateCustomerDetails(Long customerId, CustomerDto customerDto);
	public AccountDto checkBalanace(Long accountNum);
	public String deleteAccount(Long accountNum);
}
