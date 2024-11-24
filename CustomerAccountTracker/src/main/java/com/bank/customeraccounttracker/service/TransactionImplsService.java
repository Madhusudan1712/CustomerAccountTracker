package com.bank.customeraccounttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.customeraccounttracker.dto.FundTransferRequestDto;
import com.bank.customeraccounttracker.model.AccountModel;
import com.bank.customeraccounttracker.model.TransactionModel;
import com.bank.customeraccounttracker.repository.AccountRepository;
import com.bank.customeraccounttracker.repository.TransactionRepository;

@Service
public class TransactionImplsService implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    //fund transfer from one account to another
    @Transactional
    public String transferFunds(FundTransferRequestDto request) {
        // Fetch both accounts
        AccountModel fromAccount = accountRepository.findByAccountNumber(request.getFromAccount());
        AccountModel toAccount = accountRepository.findByAccountNumber(request.getToAccount());

        if (fromAccount == null || toAccount == null) {
            return "Invalid account details.";
        }

        if (request.getAmount() <= 0) {
            return "Transfer amount must be greater than zero.";
        }

        if (fromAccount.getBalance() < request.getAmount()) {
            return "Insufficient balance in the from-account.";
        }

        // Perform balance updates
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        // Save updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Create and save debit transaction
        TransactionModel debitTransaction = new TransactionModel();
        debitTransaction.setName(fromAccount.getCustomer().getName());
        debitTransaction.setAccountNumber(fromAccount.getAccountNumber());
        debitTransaction.setTransactionType("Debit");
        debitTransaction.setTransactionAmount(request.getAmount());
        debitTransaction.setFromOrToName(toAccount.getCustomer().getName());
        debitTransaction.setFromOrToAccountNumber(toAccount.getAccountNumber());
        debitTransaction.setTotalBalance(fromAccount.getBalance());
        transactionRepository.save(debitTransaction);

        // Create and save credit transaction
        TransactionModel creditTransaction = new TransactionModel();
        creditTransaction.setName(toAccount.getCustomer().getName());
        creditTransaction.setAccountNumber(toAccount.getAccountNumber());
        creditTransaction.setTransactionType("Credit");
        creditTransaction.setTransactionAmount(request.getAmount());
        creditTransaction.setFromOrToName(fromAccount.getCustomer().getName());
        creditTransaction.setFromOrToAccountNumber(fromAccount.getAccountNumber());
        creditTransaction.setTotalBalance(toAccount.getBalance());
        transactionRepository.save(creditTransaction);

        return "Transfer successful!";
    }
    
    //transaction history by transaction id
    public TransactionModel historyByTransId(Long transactionId) {
    	TransactionModel history = transactionRepository.findById(transactionId)
    			.orElseThrow(() -> new RuntimeException("Invalid transactionId...!"));
    	return history;
	}
    
    //get all transaction history by account number
    public List<TransactionModel> getTransactionHistoryByAccountNumber(Long accountNumber) {
        List<TransactionModel> transactions = transactionRepository.findByAccountNumber(accountNumber);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for the given account number.");
        }
        return transactions;
    }
}
