package com.bank.customeraccounttracker.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "transactions")
@Entity
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Long accountNumber;
    
    private Date transactionDate = new Date();
    
    @Column(nullable = false)
    private String transactionType;
    
    @Column(nullable = false)
    private double transactionAmount;
    
    @Column(nullable = false)
    private String fromOrToName;
    
    @Column(nullable = false)
    private Long fromOrToAccountNumber;
    
    @Column(nullable = false)
    private double totalBalance;
}
