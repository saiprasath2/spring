package com.ideas2it.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.ProjectDto;

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
     * @param employeeDto - {@link EmployeeCreationDto} value to set Employee Name.
     * @return DisplayEmployeeDto value to indicate insertion status.
     */
    DisplayEmployeeDto addEmployee(EmployeeCreationDto employeeDto);

    /**
     * <p>
     * Calls retrieveEmployeeEmployees to get the Employee list.
     * </p>
     *
     * @return List<>DisplayEmployeeDto value to display Employee list.
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
     * @param employeeId -Long to remove Employee.
     * @return DisplayEmployeeDto value to acknowledge
     */
    DisplayEmployeeDto deleteEmployee(Long employeeId);

    /**
     *
     * @param employeeDto - {@link EmployeeUpdationDto} value to update the employee
     * @return DisplayEmployeeDto for acknowledgement.
     */
    DisplayEmployeeDto updateEmployee(EmployeeUpdationDto employeeDto);

    /**
     * <p>
     *     Assigns given project to the employee.
     * </p>
     * @param projectId- Long value to assign it to the employee.
     * @param employeeId - Long value to assign employee with a project.
     * @return DisplayEmployeeDto for assigning acknowledgement.
     */
    DisplayEmployeeDto assignProjectToEmployee(Long employeeId, Long projectId);
}
