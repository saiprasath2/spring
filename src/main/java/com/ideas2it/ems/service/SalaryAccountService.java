package com.ideas2it.ems.service;

import java.util.List;
import java.util.Optional;

import com.ideas2it.ems.model.SalaryAccount;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Manages the information by the following operation like creating, retrieving
 * and removing the accounts.
 * </p>
 *
 * @author Saiprasath
 * version 1.5
 */
@Service
public interface SalaryAccountService {
    /**
     * <p>
     * Passes account details to insertAccount to add or update.
     * </p>
     *
     * @param salaryAccount SalaryAccount value to add account details.
     */
    void addOrUpdateAccount(SalaryAccount salaryAccount);

    /**
     * <p>
     * Retrieves the accounts available.
     * </p>
     *
     * @return Map<> value of available projects.
     */
    List<SalaryAccount> getAccounts();

    /**
     * <p>
     * Retrieves required account by the user.
     * </p>
     *
     * @param accountId - Long value to fetch the project.
     * @return SalaryAccount value to display the project.
     */
    Optional<SalaryAccount> getAccount(Long accountId);
}

