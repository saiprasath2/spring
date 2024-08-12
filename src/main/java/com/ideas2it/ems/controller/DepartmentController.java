package com.ideas2it.ems.controller;

import java.util.List;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayableEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.mapper.DepartmentMapper;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.service.DepartmentService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private static final Logger logger = LogManager.getLogger();
    /**
     * <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param departmentService to create object.
     */
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * <p>
     * Passes the values recieved from user, to create a new Department.
     * </p>
     * @param departmentDto DepartmentDto to create the department.
     * @return dto for user acknowledgment and response.
     */
    @PostMapping("/add")
    public ResponseEntity<TransactionDepartmentDto> createDepartment(@RequestBody CreationDepartmentDto departmentDto) {
        Department department = DepartmentMapper.convertCreateToEntity(departmentDto);
        Department resultDepartment = departmentService.addOrUpdateDepartment(department);
        logger.info(resultDepartment.getDepartmentName() + " department created successfully");
        return new ResponseEntity<>(DepartmentMapper.convertToTransactionDto(resultDepartment), HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific department mentioned by the user.
     * </p>
     *
     * @param departmentId - to fetch the department.
     * @return required department and response to user.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<TransactionDepartmentDto> getDepartment(@PathVariable(name = "id") Long departmentId) {
        Department department = departmentService.getDepartment(departmentId);
        if (null == department) {
            logger.error("Department with Id : " + departmentId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DepartmentMapper.convertToTransactionDto(department), HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted departments.
     * </p>
     *
     * @return record of departments.
     */
    @GetMapping("/get")
    public ResponseEntity<List<TransactionDepartmentDto>> getAllDepartments() {
        List<TransactionDepartmentDto> responseDepartments = departmentService.getDepartments().stream()
                                                           .map(DepartmentMapper::convertToTransactionDto).toList();
        return new ResponseEntity<>(responseDepartments, HttpStatus.OK);
    }

    /**
     * <p>
     *
     * </p>
     * @param departmentId - to detect the department
     * @param departmentDto - to pass the fields.
     * @return update dto for user acknowledgement and response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<TransactionDepartmentDto> updateDepartment(@PathVariable(name = "id") Long departmentId,
                                                           @RequestBody TransactionDepartmentDto departmentDto ) {
        Department department = DepartmentMapper.convertTransactionToEntity(departmentDto);
        department.setDepartmentName(departmentDto.getName());
        Department resultDepartment = departmentService.addOrUpdateDepartment(department);
        if (null == resultDepartment) {
            logger.error("Department with Id : " + departmentId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info(resultDepartment.getDepartmentName() + " created successfully.");
        return new ResponseEntity<>(DepartmentMapper.convertToTransactionDto(resultDepartment), HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Recieves the department from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param departmentId to remove Department.
     * @return Department value to acknowledge.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<CreationDepartmentDto> deleteDepartment(@PathVariable(name = "id") Long departmentId) {
        Department department = departmentService.getDepartment(departmentId);
        if (null == department) {
            logger.error("Department with Id : " + departmentId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Department resultDepartment = departmentService.deleteDepartment(departmentId);
        logger.info(resultDepartment.getDepartmentName() + " deleted successfully");
        return new ResponseEntity<>(DepartmentMapper.convertToCreateDto(resultDepartment), HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Passes id of department to retrieve its related employees.
     * </p>
     * @param departmentId
     * @return Employee values for visual.
     */
    @GetMapping("/{sportId}/employees")
    public ResponseEntity<List<DisplayableEmployeeDto>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<DisplayableEmployeeDto> employeeDtos = departmentService.getEmployeesOfDepartments(departmentId).stream()
                .map(EmployeeMapper::convertToDisplayableDto).toList();
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}