package com.ideas2it.ems.service;

import java.sql.SQLOutput;
import java.util.List;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.AccountTransactionDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.mapper.AccountMapper;
import com.ideas2it.ems.mapper.ProjectMapper;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.repository.EmployeeRepository;

/**
 * <p>
 * Manages the information by the following operation like creating, retrieving 
 * and removing the employees.
 * </p>
 *
 * author Saiprasath
 * version 1.5
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public DisplayEmployeeDto addEmployee(EmployeeCreationDto employeeDto) {
        SalaryAccount account = new SalaryAccount(employeeDto.getAccountName(),
                                                    employeeDto.getIfscCode());
        Employee employee = EmployeeMapper.convertToEmployee(employeeDto);
        employee.setSalaryAccount(account);
        Employee resultEmployee = employeeRepository.save(employee);
        System.out.println(resultEmployee);
        return EmployeeMapper.convertToDisplayableDto(resultEmployee);
    }

    @Override
    public List<DisplayEmployeeDto> getEmployees() {
        return employeeRepository.findByIsRemovedFalse().stream()
                       .map(EmployeeMapper::convertToDisplayableDto).toList();
    }

    @Override
    public DisplayEmployeeDto getEmployee(Long employeeId) {
        return EmployeeMapper.convertToDisplayableDto(employeeRepository
                                                      .findByEmployeeIdAndIsRemoved(employeeId, false));
    }

    @Override
    public DisplayEmployeeDto deleteEmployee(Long employeeId) {
        Employee Employee = employeeRepository.findByEmployeeIdAndIsRemoved(employeeId, false);
        Employee.setIsRemoved(true);
        return EmployeeMapper.convertToDisplayableDto(employeeRepository.save(Employee));
    }

    @Override
    public DisplayEmployeeDto updateEmployee(EmployeeUpdationDto employeeDto) {
        Employee employee = EmployeeMapper.convertUpdatableDtoToEmployee(employeeDto);
        employee.setEmployeeName(employeeDto.getName());
        employee.setEmployeeDob(employeeDto.getDateOfBirth());
        employee.setContactNumber(employeeDto.getContact_number());
        SalaryAccount salaryAccount = new SalaryAccount(employeeDto.getAccountName(),employeeDto.getIfscCode());
        employee.setSalaryAccount(salaryAccount);
        Department department = new Department(employeeDto.getDepartmentId());
        employee.setDepartment(department);
        Employee resultEmployee = employeeRepository.save(employee);
        return EmployeeMapper.convertToDisplayableDto(resultEmployee);
    }

    @Override
    public DisplayEmployeeDto assignProjectToEmployee(Long employeeId, ProjectDto projectDto) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsRemoved(employeeId, false);
        Project project = ProjectMapper.convertToEntity(projectDto);
        employee.getProject().add(project);
        Employee resultEmployee = employeeRepository.save(employee);
        return EmployeeMapper.convertToDisplayableDto(resultEmployee);
    }
}