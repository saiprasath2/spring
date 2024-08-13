package com.ideas2it.ems.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.ProjectDto;
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
     *  <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param projectService - {@link EmployeeService} value to create object.
     * @param employeeService - {@link ProjectService} value to create object.
     */
    public EmployeeController(EmployeeService employeeService,
                              ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    /**
     * <p>
     *     Passes the values recieved from user, to create a new Employee.
     * </p>
     * @param employeeDto - {@link EmployeeCreationDto} value to create the Employee.
     * @return dto for user acknowledgment and response.
     */
    @PostMapping
    public ResponseEntity<DisplayEmployeeDto> createEmployee(@RequestBody EmployeeCreationDto employeeDto) {
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
        if (null == employeeDto) {
            logger.warn("Employee with Id : {}not found.", employeeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<DisplayEmployeeDto> updateEmployee(@RequestBody EmployeeUpdationDto employeeDto) {
        DisplayEmployeeDto resultDto = employeeService.updateEmployee(employeeDto);
        if (null == resultDto) {
            logger.warn("Employee with Id : {}not found to find.", employeeDto.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("{} created successfully.", resultDto.getName());
            return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
        }
    }

    /**
     * <p>
     *     Receives the Employee from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param employeeId - Long value to remove Employee.
     * @return DisplayEmployeeDto value to acknowledge.
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long employeeId) {
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        if (null == employeeDto) {
            logger.warn("Employee with Id : {}not found to delete.", employeeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            DisplayEmployeeDto resultDto = employeeService.deleteEmployee(employeeId);
            logger.info("{} deleted successfully", resultDto.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
        DisplayEmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        boolean isAssigned = false;
        if (null == employeeDto) {
            logger.warn("Employee with Id : {}not found to assign", employeeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ProjectDto projectDto = projectService.getProject(projectId);
            if (projectDto != null) {
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
                    DisplayEmployeeDto resultDto = employeeService.assignProjectToEmployee(employeeId, projectDto);
                    logger.info("Id : {} Assigned Successfully!", employeeId);
                    return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
                }
            } else {
                logger.info("No project with Id :{}", projectId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}