package com.bank.customeraccounttracker.service;

import java.util.List;

import com.bank.customeraccounttracker.dto.FundTransferRequestDto;
import com.bank.customeraccounttracker.model.TransactionModel;

public interface TransactionService {
	public String transferFunds(FundTransferRequestDto request);
	public TransactionModel historyByTransId(Long transactionId);
	public List<TransactionModel> getTransactionHistoryByAccountNumber(Long accountNumber);
}
