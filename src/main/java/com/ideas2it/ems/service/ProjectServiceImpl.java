package com.ideas2it.ems.service;

import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Project addOrUpdateProject(Project Project) {
        return projectRepository.save(Project);
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.getAllNotDeletedProjects();
    }

    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findByProjectIdAndIsRemoved(projectId, false);
    }

    @Override
    public Project deleteProject(Long projectId) {
        Project Project = projectRepository.findByProjectIdAndIsRemoved(projectId, false);
        Project.setIsRemoved(true);
        return projectRepository.save(Project);
    }
}