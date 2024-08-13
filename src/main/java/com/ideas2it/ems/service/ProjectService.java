package com.ideas2it.ems.service;

import java.util.List;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;

import org.springframework.stereotype.Service;

/**
 * <p>
 * Passes value for insertion,deletion and retrieval on Project.
 * </p>
 *
 * @author   Saiprasath
 * @version  1.4
 */
@Service
public interface ProjectService {

    /**
     * <p>
     *     passes the value for insertion into the collection.
     * </p>
     *
     * @param projectDto - {@link ProjectDto} value to set Project Name.
     * @return ProjectDto value to indicate insertion status.
     */
    ProjectDto addOrUpdateProject(ProjectDto projectDto);

    /**
     * <p>
     *     Calls retrieveEmployeeProjects to get the Project list.
     * </p>
     *
     * @return List<ProjectDto> value to display Project list.
     */
    List<ProjectDto> getProjects();

    /**
     * <p>
     *     Calls the getProject to fetch the Project and returns the Project.
     * </p>
     *
     * @param projectId - Long value to display the Project.
     * @return ProjectDto value to display the Project.
     */
    ProjectDto getProject(Long projectId);

    /**
     * <p>
     *     Removes the Project from user view by changing boolean value.
     * </p>
     * @param projectId - Long to remove Project.
     * @return ProjectDto value to acknowledge.
     */
    ProjectDto deleteProject(Long projectId);

    /**
     * <p>
     *     Updates the given project with the new name.
     * </p>
     *
     * @param projectDto - {@link ProjectDto} value to update project.
     * @return ProjectDto for acknowledgement.
     */
    ProjectDto updateProject(ProjectDto projectDto);

    /**
     * <p>
     *     Returns employee record under the specific project.
     * </p>
     *
     * @param projectId - Long value to get the project.
     * @return List<> {@link DisplayEmployeeDto} value to list the employee.
     */
    List<DisplayEmployeeDto> getEmployeesOfProjects(Long projectId);
}
