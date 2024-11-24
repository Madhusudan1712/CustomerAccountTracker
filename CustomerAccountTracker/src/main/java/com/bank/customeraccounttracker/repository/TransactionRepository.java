package com.bank.customeraccounttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customeraccounttracker.model.TransactionModel;
@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
	 List<TransactionModel> findByAccountNumber(Long accountNumber);
}
