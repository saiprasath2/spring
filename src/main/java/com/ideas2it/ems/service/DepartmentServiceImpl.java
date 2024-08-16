package com.ideas2it.ems.service;

import java.util.List;
import java.util.Objects;

import com.ideas2it.ems.exception.EntityAlreadyExistsException;
import com.ideas2it.ems.exception.EntityNotFoundException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private static final Logger logger = LogManager.getLogger();

    @Override
    public TransactionDepartmentDto addDepartment(CreationDepartmentDto departmentDto) {
        Department department = DepartmentMapper.convertCreateToEntity(departmentDto);
        List<Department> departments = departmentRepository.getAllNotDeletedDepartments();
        for (Department iterator : departments) {
            if (Objects.equals(department.getDepartmentName(), iterator.getDepartmentName())) {
                throw new EntityAlreadyExistsException("Department name : " + department.getDepartmentName() + " already exists.");
            }
        }
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
        if (null == department) {
            logger.warn("Department with Id : {}not found.", departmentId);
            throw new EntityNotFoundException("Department with Id : " + departmentId + "not found.");
        }
        return DepartmentMapper.convertToTransactionDto(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        if (null == department) {
            logger.warn("Department with Id : {}not found to delete.", departmentId);
            throw new EntityNotFoundException("Department with Id : " + departmentId + "not found.");
        }
        department.setIsRemoved(true);
        departmentRepository.save(department);
    }

    @Override
    public TransactionDepartmentDto updateDepartment(TransactionDepartmentDto departmentDto) {
        Department department = DepartmentMapper.convertTransactionToEntity(departmentDto);
        if (null == getDepartment(department.getDepartmentId())) {
            logger.warn("Department with Id : {} not found to update.", departmentDto.getId());
            throw new EntityNotFoundException("Department with Id : " + departmentDto.getId() + "not found.");
        }
        department.setDepartmentName(departmentDto.getName());
        return DepartmentMapper.convertToTransactionDto(department);
    }

    @Override
    public List<DisplayEmployeeDto> getEmployeesOfDepartments(Long departmentId) {
        Department department = departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false);
        if (null == department) {
            logger.warn("Department with Id : {} not found to fetch.", departmentId);
            throw new EntityNotFoundException("Department with Id : " + departmentId + "not found.");
        }
        return department.getEmployees().stream()
                .map(EmployeeMapper::convertToDisplayableDto).toList();
    }
}