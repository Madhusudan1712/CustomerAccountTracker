package com.bank.customeraccounttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.customeraccounttracker.dto.AccountDto;
import com.bank.customeraccounttracker.dto.CustomerDto;
import com.bank.customeraccounttracker.mapper.AllMapper;
import com.bank.customeraccounttracker.model.AccountModel;
import com.bank.customeraccounttracker.model.AccountType;
import com.bank.customeraccounttracker.model.CustomerModel;
import com.bank.customeraccounttracker.repository.AccountRepository;
import com.bank.customeraccounttracker.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerAccountServiceImpls implements CustomerAccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String createAccount(CustomerDto dto) {
    	dto.setBankName("Karnataka Bank");
    	
        if (dto.getAccounts() == null || dto.getAccounts().size() != 1) {
            return "Only one account can be created at a time.";
        }

        // Check if the customer already exists based on Aadhaar number
        CustomerModel customer = customerRepository.findByAadhaarNumber(dto.getAadhaarNumber());

        if (customer == null) {
            // If customer doesn't exist, create and save a new customer
            customer = AllMapper.toCustomerEntity(dto);
            customer = customerRepository.save(customer);
        }

        // Check if the customer already has an account with the requested account type
        AccountDto accountDto = dto.getAccounts().get(0); // Assuming only one account is passed for creation
        //accountDto.setBalance(1000.0); // Default balance
        accountDto.setCustomerId(customer.getCustomerId()); // Associate the customer ID with the account

        // If no account type is specified, default to SAVINGS
        if (accountDto.getAccountType() == null) {
            accountDto.setAccountType(AccountType.SAVINGS);
        }

        // Validate minimum balance
        if (accountDto.getBalance() == null || accountDto.getBalance() < 999) {
            return "Minimum balance of 1000 is required to open an account.";
        }

        // Check if an account of the same type already exists for this customer
        List<AccountModel> existingAccounts = accountRepository.findByCustomer(customer);
        if (existingAccounts.stream().anyMatch(account -> account.getAccountType() == accountDto.getAccountType())) {
            return "Account with this type (" + accountDto.getAccountType() + ") already exists for customer with ID: " + customer.getCustomerId();
        }

        // Map the AccountDto to AccountModel
        AccountModel account = AllMapper.toAccountEntity(accountDto);
        account.setCustomer(customer); // Set the customer reference in the account model
        customer.getAccounts().add(account);

        // Save the account to the repository
        accountRepository.save(account);

        return "Account created successfully with account number: " + account.getAccountNumber();
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //get full customer information
    @Override
    public CustomerDto getCustomerDetailsById(Long customerId) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found"));
        
        return AllMapper.toCustomerDto(customer);
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    // Update customer details
    public String updateCustomerDetails(Long customerId, CustomerDto customerDto) {
        // Fetch the existing customer
        CustomerModel customer = customerRepository.findByCustomerId(customerId);
        
        if(customer == null) {
        	return "Customer ID does't exits, update faild ...!";
        }
        
        if(customerDto.getAccounts() != null) {
        	return "Not allowd to edit account details, update faild ...!";
        }

        // Update the allowed fields
        if (customerDto.getName() != null) {
            customer.setName(customerDto.getName());
        }
        if (customerDto.getMobileNum() != null) {
            customer.setMobileNum(customerDto.getMobileNum());
        }
        if (customerDto.getEmailId() != null) {
            customer.setEmailId(customerDto.getEmailId());
        }
        if (customerDto.getAddress() != null) {
            customer.setAddress(customerDto.getAddress());
        }

        // Save the updated customer back to the repository
        customerRepository.save(customer);
        return "Customer details updated successfully.";
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Able to check balance by account number.
    
    public AccountDto checkBalanace(Long accountNum) {
    	AccountModel account = accountRepository.findByAccountNumber(accountNum);
    	if(account == null) {
    		throw new RuntimeException("Account with number " + accountNum + " not found.");
    	}
    	return AllMapper.toAccountDto(account);
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //delete an account by account number
    @Transactional
    public String deleteAccount(Long accountNum) {
        // Fetch the account by account number
        AccountModel account = accountRepository.findById(accountNum)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist, delete failed!"));

        // Remove the account from the customer's account list
        CustomerModel customer = account.getCustomer();
        customer.getAccounts().remove(account);

        // Save the customer to ensure orphan removal is triggered
        customerRepository.save(customer);

        return "Account deleted successfully";
    }


}
