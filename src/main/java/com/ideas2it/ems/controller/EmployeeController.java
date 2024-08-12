package com.ideas2it.ems.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.service.EmployeeService;
import com.ideas2it.ems.service.ProjectService;

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
    private final ProjectService projectService;
    private static final Logger logger = LogManager.getLogger();

    /**
     *  Creates object for service layer inside controller.
     * </p>
     * @param projectService to create object.
     * @param employeeService to create object.
     */
    public EmployeeController(EmployeeService employeeService,
                              ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    /**
     * <p>
     * Passes the values recieved from user, to create a new Employee.
     * </p>
     * @param employeeDto {@link EmployeeCreationDto} EmployeeDto to create the Employee.
     * @return dto for user acknowledgment and response.
     */
    @PostMapping
    public ResponseEntity<DisplayEmployeeDto> createEmployee(@RequestBody EmployeeCreationDto employeeDto) {
        DisplayEmployeeDto resultEmployee = employeeService.addOrUpdateEmployee(employeeDto);
        logger.info(resultEmployee.getId() + " Employee created successfully");
        return new ResponseEntity<>(resultEmployee, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific Employee mentioned by the user.
     * </p>
     *
     * @param employeeId - to fetch the Employee.
     * @return required Employee and response to user.
     */
    @GetMapping("{id}")
    public ResponseEntity<DisplayEmployeeDto> getEmployee(@PathVariable(name = "id") Long employeeId) {
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        if (null == employeeDto) {
            logger.warn("Employee with Id : " + employeeId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted Employees.
     * </p>
     *
     * @return record of Employees.
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
     * @param employeeDto - {@link EmployeeUpdationDto}to pass the fields.
     * @return update dto for user acknowledgement and response.
     */
    @PutMapping()
    public ResponseEntity<DisplayEmployeeDto> updateEmployee(@RequestBody EmployeeUpdationDto employeeDto) {
        DisplayEmployeeDto resultDto = employeeService.updateEmployee(employeeDto);
        if (null == resultDto) {
            logger.warn("Employee with Id : " + employeeDto.getId() + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info(resultDto.getName() + " created successfully.");
            return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
        }
    }

    /**
     * <p>
     *     Recieves the Employee from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param employeeId to remove Employee.
     * @return Employee value to acknowledge.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<DisplayEmployeeDto> deleteEmployee(@PathVariable(name = "id") Long employeeId) {
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        if (null == employeeDto) {
            logger.warn("Employee with Id : " + employeeId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            DisplayEmployeeDto resultDto = employeeService.deleteEmployee(employeeId);
            logger.info(resultDto.getName() + " deleted successfully");
            return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
        }
    }

    /**
     * <p>
     *     Recieves the Employee from user and project to assign it.
     * </p>
     * @param employeeId to assign Project.
     * @param projectId to assign under employee.
     * @return Employee value to acknowledge.
     */
    @PutMapping("/{id}/assign/{projectid}")
    public ResponseEntity<DisplayEmployeeDto> assignProject(@PathVariable(name = "id") Long employeeId,
                                                                @PathVariable(name = "projectid") Long projectId) {
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        boolean isAssigned = false;
        if (null == employeeDto) {
            logger.warn("Employee with Id : " + employeeId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Project project = projectService.getProject(projectId);
            if (project != null) {
                List<DisplayEmployeeDto> employeeDtoRecord = projectService.getEmployeesOfProjects(projectId);
                for (DisplayEmployeeDto employees : employeeDtoRecord) {
                    if (employees.getId() == employeeId) {
                        isAssigned = true;
                        break;
                    }
                }
                if (isAssigned) {
                    logger.warn("Enter valid input. "
                                + "Employee already assigned"
                                + " with this project.");
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    Employee resultEmployee = employeeService.assignProjectToEmployee(project, employee);
                    logger.info("Id : " + employeeId + " Assigned Successfully!");
                    return new ResponseEntity<>(EmployeeMapper.convertToDisplayableDto(resultEmployee), HttpStatus.ACCEPTED);
                }
            } else {
                logger.info("No project with Id :" + projectId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}