package com.bank.customeraccounttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customeraccounttracker.model.CustomerModel;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
	 CustomerModel findByAadhaarNumber(Long aadhaarNum);
	 CustomerModel findByCustomerId(Long customerId);
}
