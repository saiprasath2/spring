package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.ems.model.Employee;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the employee.
 *
 * Handles data's of employee along with department and project entity.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByIsRemovedFalse();

    Employee findByEmployeeIdAndIsRemoved(Long id, boolean isDeleted);
}