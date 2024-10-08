package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.SalaryAccount;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class EmployeeMapper {

    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param employee {@link Employee} for conversion into dto.
     * @return DisplayEmployeeDto to display.
     */
    public static DisplayEmployeeDto convertToDisplayableDto(Employee employee) {
        return new DisplayEmployeeDto(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getContactNumber(),
                employee.getAge(),
                employee.getSalaryAccount().getAccountName(),
                employee.getSalaryAccount().getIfscCode(),
                employee.getDepartment().getDepartmentName(),
                employee.getAllProjects());
    }

    /**
     * <p>
     *     Converts input dto to entity for creating employee.
     * </p>
     * @param employeeDto {@link EmployeeCreationDto} to convert into entity.
     * @return Employee to for internal usage.
     */
    public static Employee convertToEmployee(EmployeeCreationDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDto.getName());
        employee.setEmployeeDob(employeeDto.getDateOfBirth());
        employee.setContactNumber(employeeDto.getContact_number());
        Department department = new Department(employeeDto.getDepartmentId());
        SalaryAccount salaryAccount = new SalaryAccount(employeeDto.getAccountName(), employeeDto.getIfscCode());
        employee.setSalaryAccount(salaryAccount);
        employee.setDepartment(department);
        return employee;
    }
}
