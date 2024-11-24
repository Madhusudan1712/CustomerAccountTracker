package com.bank.customeraccounttracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.customeraccounttracker.dto.AccountDto;
import com.bank.customeraccounttracker.dto.CustomerDto;
import com.bank.customeraccounttracker.dto.FundTransferRequestDto;
import com.bank.customeraccounttracker.model.TransactionModel;
import com.bank.customeraccounttracker.service.CustomerAccountService;
import com.bank.customeraccounttracker.service.TransactionService;

@RestController
@RequestMapping("/api/accounts")
public class BankController {

    @Autowired
    private CustomerAccountService bankService;
    
    @Autowired
    private TransactionService transactionService;

    //create a new account
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CustomerDto customerDto) {
        String responseMessage = bankService.createAccount(customerDto);
        HttpStatus status = responseMessage.contains("successfully") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(responseMessage, status);
    }
    
    //get full customer information
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable Long customerId) {
        CustomerDto customerDto = bankService.getCustomerDetailsById(customerId);
        return ResponseEntity.ok(customerDto);
    }
    
    //update personal data by customer id
    @PutMapping("/updateCustomerDetails/{customerId}")
    public ResponseEntity<String> updateCustomerDetails(@PathVariable Long customerId, @RequestBody CustomerDto customerDto) {
        String response = bankService.updateCustomerDetails(customerId, customerDto);
        return ResponseEntity.ok(response);
    }
    
    //check balance by account number
    @GetMapping("/balanceByAccNo/{accountNum}")
    public ResponseEntity<AccountDto> checkBalanace(@PathVariable Long accountNum) {
    	AccountDto accountDto = bankService.checkBalanace(accountNum);
    	return ResponseEntity.ok(accountDto);
    }
    
    //delete an account by account number
    @DeleteMapping("/deleteByAccNum/{accountNum}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNum) {
    	String delete = bankService.deleteAccount(accountNum);
    	return ResponseEntity.ok(delete);
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //transfer fund
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody FundTransferRequestDto request) {
        String result = transactionService.transferFunds(request);
        return ResponseEntity.ok(result);
    }
    
    //full transactions by account number
    @GetMapping("/transactionHistory/{accountNumber}")
    public ResponseEntity<List<TransactionModel>> getTransactionHistory(@PathVariable Long accountNumber) {
        List<TransactionModel> transactions = transactionService.getTransactionHistoryByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }
    
    //transaction by trasactionId
    @GetMapping("/History/{transactionId}")
    public TransactionModel historyByTransId(@PathVariable Long transactionId) {
    	return transactionService.historyByTransId(transactionId);
    }
    
}
