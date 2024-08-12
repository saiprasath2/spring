package com.ideas2it.ems.service;

import java.util.List;
import java.util.Set;

import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Manages the information by the following operation like creating, retrieving 
 * and removing the employees.
 * </p>
 *
 * author Saiprasath
 * version 1.4
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department addOrUpdateDepartment(Department department) {
        return departmentRepository.save(department);
    }
 
    @Override
    public List<Department> getDepartments() {
        return departmentRepository.getAllNotDeletedDepartments();
    }
    
    @Override
    public Department getDepartment(Long departmentId) {
        return departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
    }

    @Override
    public Department deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        department.setIsRemoved(true);
        return departmentRepository.save(department);
    }

    @Override
    public Set<Employee> getEmployeesOfDepartments(Long departmentId) {
        Department department = getDepartment(departmentId);
        return department.getEmployees();
    }
}