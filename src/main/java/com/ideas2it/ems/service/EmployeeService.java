package com.ideas2it.ems.service;

import java.util.List;

import com.ideas2it.ems.dto.EmployeeUpdationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * Passes value for insertion,deletion,updation
 * and retrieval on Employee.
 * </p>
 *
 * @author   Saiprasath
 * @version  1.5
 */
@Service
public interface EmployeeService {
    /**
     * <p>
     * passes the value for insertion into the collection.
     * </p>
     *
     * @param employeeDto {@link EmployeeCreationDto}  value to set Employee Name.
     * @return DisplayEmployeeDto value to indicate insertion status.
     */
    DisplayEmployeeDto addOrUpdateEmployee(EmployeeCreationDto employeeDto);

    /**
     * <p>
     * Calls retrieveEmployeeEmployees to get the Employee list.
     * </p>
     *
     * @return List<> value to display Employee list.
     */
    List<DisplayEmployeeDto> getEmployees();

    /**
     * <p>
     * Calls the getEmployee to fetch the Employee and returns the Employee.
     * </p>
     *
     * @param employeeId  Long value to display the Employee.
     * @return DisplayEmployeeDto value to display the Employee.
     */
    DisplayEmployeeDto getEmployee(Long employeeId);

    /**
     * <p>
     *     Removes the Employee from user view by changing boolean value.
     * </p>
     *
     * @param employeeId to remove Employee.
     * @return DisplayEmployeeDto value to acknowledge
     */
    DisplayEmployeeDto deleteEmployee(Long employeeId);

    /**
     *
     * @param employeeDto {@link EmployeeUpdationDto} - to update the employee
     * @return DisplayEmployeeDto for acknowledgement.
     */
    DisplayEmployeeDto updateEmployee(EmployeeUpdationDto employeeDto);

    /**
     * <p>
     *     Assigns given project to the employee.
     * </p>
     * @param project - to assign it to the employee.
     * @param employee - to assign employee with a project.
     * @return Employee for assigning acknowledgement.
     */
    Employee assignProjectToEmployee(Project project, Employee employee);
}
