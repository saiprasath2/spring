package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.model.Project;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 */
public class ProjectMapper {
    public static ProjectDto convertToDto(Project Project) {
        return new ProjectDto(Project.getProjectId(), Project.getProjectName());
    }

    public static Project convertToEntity(ProjectDto ProjectDto) {
        Project Project = new Project();
        Project.setProjectId(ProjectDto.getId());
        Project.setProjectName(ProjectDto.getName());
        return Project;
    }
}
