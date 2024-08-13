package com.ideas2it.ems.service;

import java.util.List;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.mapper.ProjectMapper;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Manages the information by the following operation like creating, retrieving 
 * and removing the employees.
 * </p>
 *
 * author Saiprasath
 * version 1.4
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public ProjectDto addOrUpdateProject(ProjectDto projectDto) {
        Project project = ProjectMapper.convertToEntity(projectDto);
        Project resultProject = projectRepository.save(project);
        return ProjectMapper.convertToDto(resultProject);
    }

    @Override
    public List<ProjectDto> getProjects() {
        return projectRepository.findByIsRemovedFalse().stream()
                .map(ProjectMapper::convertToDto).toList();
    }

    @Override
    public ProjectDto getProject(Long projectId) {
        return ProjectMapper.convertToDto(projectRepository.findByProjectIdAndIsRemoved(projectId, false));
    }

    @Override
    public ProjectDto deleteProject(Long projectId) {
        Project Project = projectRepository.findByProjectIdAndIsRemoved(projectId, false);
        Project.setIsRemoved(true);
        return ProjectMapper.convertToDto(projectRepository.save(Project));
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        Project project = ProjectMapper.convertToEntity(projectDto);
        project.setProjectName(projectDto.getName());
        Project resultProject = projectRepository.save(project);
        return ProjectMapper.convertToDto(resultProject);
    }

    @Override
    public List<DisplayEmployeeDto> getEmployeesOfProjects(Long projectId) {
        Project project = projectRepository.findByProjectIdAndIsRemoved(projectId, false);
        return project.getEmployees().stream()
                        .map(EmployeeMapper::convertToDisplayableDto).toList();
    }
}