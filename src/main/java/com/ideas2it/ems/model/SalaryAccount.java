package com.ideas2it.ems.model;

import jakarta.persistence.*;

/**
 * <p> Represents blueprint for the salary account datatype.
 * Contains details of Account such as Id, name.
 * Includes collections of employee alotted to a Account.
 * </p>
 *
 * @author Saiprasath
 * @version 1.4
 */
@Entity
@Table(name = "salary_account")
public class SalaryAccount {

    @Id
    @Column(name = "account_id")
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long accountId;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountName;

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    public SalaryAccount() {}

    public SalaryAccount(String accountName, String ifscCode) {
        this.accountName = accountName;
        this.ifscCode = ifscCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
