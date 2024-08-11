package com.ideas2it.ems.service;

import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.repository.SalaryAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryAccountServiceImpl implements SalaryAccountService {
    @Autowired
    SalaryAccountRepository salaryAccountRepository;

    @Override
    public void addOrUpdateAccount(SalaryAccount salaryAccount) {
       salaryAccountRepository.save(salaryAccount);
    }

    @Override
    public List<SalaryAccount> getAccounts() {
        return salaryAccountRepository.findAll();
    }

    @Override
   public Optional<SalaryAccount> getAccount(Long accountId) {
        return salaryAccountRepository.findById(accountId);
    }
}
