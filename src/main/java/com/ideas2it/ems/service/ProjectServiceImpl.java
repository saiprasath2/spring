package com.ideas2it.ems.service;

import java.util.List;
import java.util.Objects;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.exception.EntityAlreadyExistsException;
import com.ideas2it.ems.exception.EntityNotFoundException;
import com.ideas2it.ems.mapper.EmployeeMapper;
import com.ideas2it.ems.mapper.ProjectMapper;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.repository.ProjectRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public ProjectDto addProject(ProjectDto projectDto) {
        Project project = ProjectMapper.convertToEntity(projectDto);
        List<Project> projects = projectRepository.findByIsRemovedFalse();
        for (Project iterator : projects) {
            if (Objects.equals(project.getProjectName(), iterator.getProjectName())) {
                throw new EntityAlreadyExistsException("Project name : " + project.getProjectName() + " already exists.");
            }
        }
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
        Project project = projectRepository.findByProjectIdAndIsRemoved(projectId, false);
        if (null == project) {
            logger.warn("Project with Id : {}not found.", projectId);
            throw new EntityNotFoundException("Project with Id : " + projectId + "not found.");
        }
        return ProjectMapper.convertToDto(project);
    }

    @Override
    public ProjectDto deleteProject(Long projectId) {
        Project project = projectRepository.findByProjectIdAndIsRemoved(projectId, false);
        if (null == project) {
            logger.warn("Project with Id : {}not found to delete.", projectId);
            throw new EntityNotFoundException("Project with Id : " + projectId + "not found.");
        }
        project.setIsRemoved(true);
        return ProjectMapper.convertToDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto) {
        Project project = ProjectMapper.convertToEntity(projectDto);
        if (null == getProject(project.getProjectId())) {
            logger.warn("Project with Id : {}not found to update.", projectDto.getId());
            throw new EntityNotFoundException("Project with Id : " + projectDto.getId() + "not found.");
        }
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