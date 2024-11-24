package com.bank.customeraccounttracker.dto;

import java.util.List;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

	// private Long customerId;
	private String name;
	private Long aadhaarNumber;
	@Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
	private String mobileNum;
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email address")
	private String emailId;
	private String bankName;
	private String address;
	// private Date createdAt;
	private List<AccountDto> accounts;
}
