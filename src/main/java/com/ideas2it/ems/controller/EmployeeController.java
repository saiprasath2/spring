package com.ideas2it.ems.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.service.EmployeeService;

/**
 * <p>
 * EmployeeController handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new Employee details, employee details.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private static final Logger logger = LogManager.getLogger();

    /**
     *  <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param employeeService - {@link EmployeeService} value to create object.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * <p>
     *     Passes the values recieved from user, to create a new Employee.
     * </p>
     * @param employeeDto - {@link EmployeeCreationDto} value to create the Employee.
     * @return dto for user acknowledgment and response.
     */
    @PostMapping
    public ResponseEntity<DisplayEmployeeDto> createEmployee(@Valid @RequestBody EmployeeCreationDto employeeDto) {
        DisplayEmployeeDto resultEmployee = employeeService.addEmployee(employeeDto);
        logger.info("{} Employee created successfully", resultEmployee.getId());
        return new ResponseEntity<>(resultEmployee, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific Employee mentioned by the user.
     * </p>
     *
     * @param employeeId - Long value to fetch the Employee.
     * @return required Employee and response to user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DisplayEmployeeDto> getEmployee(@PathVariable(name = "id") Long employeeId) {
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted Employees.
     * </p>
     *
     * @return List<>DisplayEmployeeDto record of Employees.
     */
    @GetMapping
    public ResponseEntity<List<DisplayEmployeeDto>> getAllEmployees() {
        List<DisplayEmployeeDto> responseEmployees = employeeService.getEmployees();
        return new ResponseEntity<>(responseEmployees, HttpStatus.OK);
    }

    /**
     * <p>
     *    Updates the given employee.
     * </p>
     *
     * @param employeeDto - {@link EmployeeUpdationDto} value to pass the fields.
     * @return DisplayEmployeeDto for user acknowledgement and response.
     */
    @PutMapping()
    public ResponseEntity<DisplayEmployeeDto> updateEmployee(@Valid @RequestBody EmployeeUpdationDto employeeDto) {
        DisplayEmployeeDto resultDto = employeeService.updateEmployee(employeeDto);
        logger.info("{} created successfully.", resultDto.getName());
        return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Receives the Employee from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param employeeId - Long value to remove Employee.
     * @return DisplayEmployeeDto value to acknowledge.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long employeeId) {
        DisplayEmployeeDto resultDto = employeeService.deleteEmployee(employeeId);
        logger.info("{} deleted successfully", resultDto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     *     Receives the Employee from user and project to assign it.
     * </p>
     * @param employeeId - Long to assign Project.
     * @param projectId - Long to assign under employee.
     * @return DisplayEmployeeDto value to acknowledge.
     */
    @PutMapping("/{id}/assign/{projectid}")
    public ResponseEntity<DisplayEmployeeDto> assignProject(@PathVariable(name = "id") Long employeeId,
                                                                @PathVariable(name = "projectid") Long projectId) {
        DisplayEmployeeDto resultDto = employeeService.assignProjectToEmployee(employeeId, projectId);
        logger.info("Id : {} Assigned Successfully!", employeeId);
        return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
    }
}