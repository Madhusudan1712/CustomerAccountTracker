package com.bank.customeraccounttracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name = "customers")
@Entity
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private Long aadhaarNumber;
    
    @Column(nullable = false, unique = true)
    private String mobileNum;
    
    @Column(nullable = false, unique = true)
    private String emailId;
    
    @Column(nullable = false)
    private String bankName;
    
    @Column(nullable = false)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AccountModel> accounts = new ArrayList<>();
    
    //----------------------------------------
    
    public CustomerModel() {
        this.createdAt = new Date();
    }
    
    public void addAccount(AccountModel account) {
        this.accounts.add(account);
        account.setCustomer(this);
    }

    public void removeAccount(AccountModel account) {
        this.accounts.remove(account);
        account.setCustomer(null);
    }

}
