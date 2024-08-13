package com.ideas2it.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.mapper.DepartmentMapper;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.repository.DepartmentRepository;

/**
 * <p>
 * Manages the information by the following operation like creating, retrieving 
 * and removing the Departments.
 *
 * </p>
 *
 * author Saiprasath
 * version 1.5
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public TransactionDepartmentDto addDepartment(CreationDepartmentDto departmentDto) {
        Department department = DepartmentMapper.convertCreateToEntity(departmentDto);
        Department resultDepartment = departmentRepository.save(department);
        return DepartmentMapper.convertToTransactionDto(resultDepartment);
    }
 
    @Override
    public List<TransactionDepartmentDto> getDepartments() {
        return departmentRepository.getAllNotDeletedDepartments().stream()
                  .map(DepartmentMapper::convertToTransactionDto).toList();
    }
    
    @Override
    public TransactionDepartmentDto getDepartment(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        return DepartmentMapper.convertToTransactionDto(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        department.setIsRemoved(true);
        departmentRepository.save(department);
    }

    @Override
    public TransactionDepartmentDto updateDepartment(TransactionDepartmentDto departmentDto) {
        Department department = DepartmentMapper.convertTransactionToEntity(departmentDto);
        department.setDepartmentName(departmentDto.getName());
        return DepartmentMapper.convertToTransactionDto(department);
    }

    @Override
    public List<DisplayEmployeeDto> getEmployeesOfDepartments(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        return department.getEmployees().stream()
                .map(EmployeeMapper::convertToDisplayableDto).toList();
    }
}