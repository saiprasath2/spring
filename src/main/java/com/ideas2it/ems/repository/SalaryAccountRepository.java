package com.ideas2it.ems.repository;

import com.ideas2it.ems.model.SalaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryAccountRepository extends JpaRepository<SalaryAccount, Long> {
}
