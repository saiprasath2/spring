package com.ideas2it.ems.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.service.DepartmentService;

/**
 * <p>
 * DepartmentController handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new department details, employee details.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private static final Logger logger = LogManager.getLogger();
    /**
     * <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param departmentService - {@link DepartmentService} value to create object.
     */
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * <p>
     *    Passes the values received from user, to create a new Department.
     * </p>
     * @param departmentDto - {@link CreationDepartmentDto} value to create the department.
     * @return TransactionDto for user acknowledgment and response.
     */
    @PostMapping
    public ResponseEntity<TransactionDepartmentDto> createDepartment(@Valid @RequestBody CreationDepartmentDto departmentDto) {
        TransactionDepartmentDto resultDto = departmentService.addDepartment(departmentDto);
        logger.info("{} department created successfully", resultDto.getName());
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific department mentioned by the user.
     * </p>
     *
     * @param departmentId - to fetch the department.
     * @return TransactionDepartmentDto required department and response to user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDepartmentDto> getDepartment(@PathVariable(name = "id") Long departmentId) {
        TransactionDepartmentDto departmentDto = departmentService.getDepartment(departmentId);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted departments.
     * </p>
     *
     * @return List<TransactionDepartmentDto> of departments.
     */
    @GetMapping
    public ResponseEntity<List<TransactionDepartmentDto>> getAllDepartments() {
        List<TransactionDepartmentDto> responseDepartments = departmentService.getDepartments();
        return new ResponseEntity<>(responseDepartments, HttpStatus.OK);
    }

    /**
     * <p>
     *    Updates name of the given department.
     * </p>
     * @param departmentDto - {@link TransactionDepartmentDto} value to pass the fields.
     * @return TransactionDepartmentDto for user acknowledgement and response.
     */
    @PutMapping
    public ResponseEntity<TransactionDepartmentDto> updateDepartment(@Valid @RequestBody TransactionDepartmentDto departmentDto) {
        TransactionDepartmentDto resultDto = departmentService.updateDepartment(departmentDto);
        logger.info("{} updated successfully.", resultDto.getName());
        return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Receives the department from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param departmentId - Long value to remove Department.
     * @return CreationDepartmentDto value to acknowledge.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable(name = "id") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        logger.info("Department with Id : {} deleted successfully", departmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     *     Passes id of department to retrieve its related employees.
     * </p>
     * @param departmentId - Long value to fetch employees.
     * @return List<DisplayEmployeeDto> values for visual.
     */
    @GetMapping("/employees/{departmentId}")
    public ResponseEntity<List<DisplayEmployeeDto>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<DisplayEmployeeDto> employeeDtos = departmentService.getEmployeesOfDepartments(departmentId);
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}