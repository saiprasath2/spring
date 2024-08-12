package com.ideas2it.ems.service;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.ems.dto.AccountTransactionDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.mapper.AccountMapper;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.repository.EmployeeRepository;

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
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProjectService projectService;

    @Override
    public DisplayEmployeeDto addOrUpdateEmployee(EmployeeCreationDto employeeDto) {
        AccountTransactionDto accountDto = new AccountTransactionDto(employeeDto.getAccountName(),
                                                                       employeeDto.getIfscCode());
        SalaryAccount account = AccountMapper.covertDtoToAccount(accountDto);
        Employee employee = EmployeeMapper.convertToEmployee(employeeDto);
        employee.setSalaryAccount(account);
        Employee resultEmployee = employeeRepository.save(employee);
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
        LocalDate dateOfBith = LocalDate.parse(employeeDto.getDateOfBirth());
        employee.setEmployeeDob(dateOfBith);
        employee.setContactNumber(employeeDto.getContact_number());
        SalaryAccount salaryAccount = new SalaryAccount(employeeDto.getAccountName(),employeeDto.getIfscCode());
        employee.setSalaryAccount(salaryAccount);
        Department department = new Department(employeeDto.getDepartmentId());
        employee.setDepartment(department);
        return EmployeeMapper.convertToDisplayableDto(employeeRepository.save(employee));
    }

    @Override
    public Employee assignProjectToEmployee(Project project, Employee employee) {
        employee.getProject().add(project);
        Employee resultEmployee = addOrUpdateEmployee(employee);
        return resultEmployee;
    }
}