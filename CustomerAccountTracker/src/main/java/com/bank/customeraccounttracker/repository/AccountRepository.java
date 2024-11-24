package com.bank.customeraccounttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customeraccounttracker.model.AccountModel;
import com.bank.customeraccounttracker.model.CustomerModel;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
	List<AccountModel> findByCustomer(CustomerModel customer);
	AccountModel findByAccountNumber(Long accountNumber);
}
