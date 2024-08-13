package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.model.Project;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class ProjectMapper {

    /**
     * <p>
     *     Converts the entity to dto format.
     * </p>
     * @param project {@link Project} for conversion into dto.
     * @return ProjectDto to display.
     */
    public static ProjectDto convertToDto(Project project) {
        return new ProjectDto(project.getProjectId(), project.getProjectName());
    }

    /**
     * <p>
     *     Converts format of dto into Entity
     *     for internal operation.
     * </p>
     * @param projectDto {@link EmployeeUpdationDto} to convert the dto.
     * @return Project to Operate.
     */
    public static Project convertToEntity(ProjectDto projectDto) {
        Project project = new Project();
        project.setProjectId(projectDto.getId());
        project.setProjectName(projectDto.getName());
        return project;
    }
}
