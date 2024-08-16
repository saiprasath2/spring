package com.ideas2it.ems.service;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.exception.EntityAlreadyExistsException;
import com.ideas2it.ems.exception.EntityNotFoundException;
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
    @Autowired
    ProjectService projectService;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public DisplayEmployeeDto addEmployee(EmployeeCreationDto employeeDto) {
        Employee employee = EmployeeMapper.convertToEmployee(employeeDto);
        Employee resultEmployee = employeeRepository.save(employee);
        System.out.println("uytghjkhg"+resultEmployee.getAllProjects());

        return EmployeeMapper.convertToDisplayableDto(resultEmployee);
    }

    @Override
    public List<DisplayEmployeeDto> getEmployees() {
        return employeeRepository.findByIsRemovedFalse().stream()
                       .map(EmployeeMapper::convertToDisplayableDto).toList();
    }

    @Override
    public DisplayEmployeeDto getEmployee(Long employeeId) {
        Employee employee = employeeRepository
                            .findByEmployeeIdAndIsRemoved(employeeId, false);
        if(employee == null) {
            logger.warn("Employee with Id : {}not found.", employeeId);
            throw new EntityNotFoundException("Employee with Id : " + employeeId + "not found.");
        }
        return EmployeeMapper.convertToDisplayableDto(employee);
    }

    @Override
    public DisplayEmployeeDto deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsRemoved(employeeId, false);
        if (null == employee) {
            logger.warn("Employee with Id : {}not found to delete.", employeeId);
            throw new EntityNotFoundException("Employee with Id : " + employeeId + "not found.");
        } else {
            employee.setIsRemoved(true);
            return EmployeeMapper.convertToDisplayableDto(employeeRepository.save(employee));
        }
    }

    @Override
    public DisplayEmployeeDto updateEmployee(EmployeeUpdationDto employeeDto) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsRemoved(employeeDto.getId(), false);
        if (null == employee) {
            logger.warn("Employee with Id : {}not found to update.", employeeDto.getId());
            throw new EntityNotFoundException("Employee with Id : " +  employeeDto.getId() + " not found.");
        } else {
            employee.setEmployeeId(employeeDto.getId());
            employee.setEmployeeName(employeeDto.getName());
            employee.setEmployeeDob(employeeDto.getDateOfBirth());
            employee.setContactNumber(employeeDto.getContact_number());
            employee.getSalaryAccount().setAccountName(employeeDto.getAccountName());
            employee.getSalaryAccount().setIfscCode(employeeDto.getIfscCode());
            Department department = new Department(employeeDto.getDepartmentId());
            employee.setDepartment(department);
            Employee resultEmployee = employeeRepository.save(employee);
            return EmployeeMapper.convertToDisplayableDto(resultEmployee);
        }
    }

    @Override
    public DisplayEmployeeDto assignProjectToEmployee(Long employeeId, Long projectId) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsRemoved(employeeId, false);
        boolean isAssigned = false;
        if (null == employee) {
            logger.warn("Employee with Id : {}not found to assign", employeeId);
            throw new EntityNotFoundException("Employee with Id : " +  employeeId + " not found.");
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
                    throw new EntityAlreadyExistsException("Employee with Id : " +  employee.getEmployeeId() + " already Assigned.");
                } else {
                    Project project = ProjectMapper.convertToEntity(projectService.getProject(projectId));
                    employee.getProject().add(project);
                    Employee resultEmployee = employeeRepository.save(employee);
                    return EmployeeMapper.convertToDisplayableDto(resultEmployee);
                }
            } else {
                logger.warn("No project with Id :{}", projectId);
                throw new EntityNotFoundException("Project with Id : " +  projectId + " not found.");
            }
        }
    }
}