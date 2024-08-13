package com.ideas2it.ems.controller;

import java.util.List;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.mapper.ProjectMapper;
import com.ideas2it.ems.service.ProjectService;
import com.ideas2it.ems.model.Project;

import org.springframework.http.HttpStatus;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * ProjectController handles all the operations related to the management based on the user's request.
 * It provide endpoints for creating new Project details, employee details.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private static final Logger logger = LogManager.getLogger();
    /**
     * <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param projectService - {@link ProjectService}to create object.
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * <p>
     * Passes the values recieved from user, to create a new Project.
     * </p>
     * @param projectDto - {@link ProjectDto} value to create the Project.
     * @return ProjectDto for user acknowledgment and response.
     */
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto resultDto = projectService.addOrUpdateProject(projectDto);
        logger.info("{} Project created successfully", resultDto.getName());
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific Project mentioned by the user.
     * </p>
     *
     * @param projectId - Long value to fetch the Project.
     * @return required ProjectDto and response to user.
     */
    @GetMapping("{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable(name = "id") Long projectId) {
        ProjectDto projectDto = projectService.getProject(projectId);
        if (null == projectDto) {
            logger.warn("Project with Id : " + projectId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted Projects.
     * </p>
     *
     * @return List<> {@link ProjectDto} type of Project records.
     */
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> responseProjects = projectService.getProjects();
        return new ResponseEntity<>(responseProjects, HttpStatus.OK);
    }

    /**
     * <p>
     *    Updates the given project name.
     * </p>
     * @param projectDto - {@link ProjectDto} to pass the fields.
     * @return ProjectDto for user acknowledgement and response.
     */
    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto ) {
        ProjectDto resultDto = projectService.updateProject(projectDto);
        if (null == resultDto) {
            logger.warn("Project with Id : " + projectDto.getId() + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info(resultDto.getName() + " created successfully.");
        return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
    }
    /**
     * <p>
     *     Receives the project from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param projectId - Long to remove Department.
     * @return ProjectDto value to acknowledge.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable(name = "id") Long projectId) {
        ProjectDto ProjectDto = projectService.getProject(projectId);
        if (null == ProjectDto) {
            logger.warn("Project with Id : " + projectId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProjectDto resultDto = projectService.deleteProject(projectId);
        logger.info("{} deleted successfully", resultDto.getName());
        return new ResponseEntity<>(resultDto, HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Passes id of project to retrieve its related employees.
     * </p>
     * @param projectId to fetch the employees.
     * @return List<DisplayEmployeeDto> {@link DisplayEmployeeDto}values for visual.
     */
    @GetMapping("/employees/{projectId}")
    public ResponseEntity<List<DisplayEmployeeDto>> getEmployeesByProjectId(@PathVariable Long projectId) {
        List<DisplayEmployeeDto> employeeDtos = projectService.getEmployeesOfProjects(projectId);
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}