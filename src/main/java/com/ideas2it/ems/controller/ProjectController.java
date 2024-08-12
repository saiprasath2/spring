package com.ideas2it.ems.controller;

import java.util.List;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.DisplayableEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.mapper.EmployeeMapper;
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
@RequestMapping("projects")
public class ProjectController {
    private final ProjectService projectService;
    private static final Logger logger = LogManager.getLogger();
    /**
     * <p>
     *     Creates object for service layer inside controller.
     * </p>
     * @param projectService to create object.
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * <p>
     * Passes the values recieved from user, to create a new Project.
     * </p>
     * @param projectDto ProjectDto to create the Project.
     * @return dto for user acknowledgment and response.
     */
    @PostMapping("/add")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        Project project = ProjectMapper.convertToEntity(projectDto);
        Project resultProject = projectService.addOrUpdateProject(project);
        logger.info(resultProject.getProjectName() + " Project created successfully");
        return new ResponseEntity<>(ProjectMapper.convertToDto(resultProject), HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Retrieves specific Project mentioned by the user.
     * </p>
     *
     * @param projectId - to fetch the Project.
     * @return required Project and response to user.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable(name = "id") Long projectId) {
        Project project = projectService.getProject(projectId);
        if (null == project) {
            logger.error("Project with Id : " + projectId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ProjectMapper.convertToDto(project), HttpStatus.OK);
    }

    /**
     * <p>
     *    Retrieves all available and not deleted Projects.
     * </p>
     *
     * @return record of Projects.
     */
    @GetMapping("/get")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> responseProjects = projectService.getProjects().stream()
                .map(ProjectMapper::convertToDto).toList();
        return new ResponseEntity<>(responseProjects, HttpStatus.OK);
    }

    /**
     * <p>
     *
     * </p>
     * @param ProjectId - to detect the Project
     * @param ProjectDto - to pass the fields.
     * @return update dto for user acknowledgement and response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable(name = "id") Long ProjectId,
                                                                     @RequestBody ProjectDto ProjectDto ) {
        Project Project = ProjectMapper.convertToEntity(ProjectDto);
        Project.setProjectName(ProjectDto.getName());
        Project resultProject = projectService.addOrUpdateProject(Project);
        if (null == resultProject) {
            logger.error("Project with Id : " + ProjectId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info(resultProject.getProjectName() + " created successfully.");
        return new ResponseEntity<>(ProjectMapper.convertToDto(resultProject), HttpStatus.ACCEPTED);
    }
    /**
     * <p>
     *     Recieves the project from user and removes it from
     *     view by changing boolean value.
     * </p>
     * @param projectId to remove Department.
     * @return Project value to acknowledge.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable(name = "id") Long projectId) {
        Project Project = projectService.getProject(projectId);
        if (null == Project) {
            logger.error("Project with Id : " + projectId + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Project resultProject = projectService.deleteProject(projectId);
        logger.info(resultProject.getProjectName() + " deleted successfully");
        return new ResponseEntity<>(ProjectMapper.convertToDto(resultProject), HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Passes id of project to retrieve its related employees.
     * </p>
     * @param projectId
     * @return Employee values for visual.
     */
    @GetMapping("/{projectId}/employees")
    public ResponseEntity<List<DisplayEmployeeDto>> getEmployeesByProjectId(@PathVariable Long projectId) {
        List<DisplayEmployeeDto> employeeDtos = projectService.getEmployeesOfProjects(projectId);
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}