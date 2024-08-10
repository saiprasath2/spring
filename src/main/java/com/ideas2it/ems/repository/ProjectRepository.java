package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.ems.model.Project;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    String projectQuery = "from Project where isRemoved = false";

    @Query(projectQuery)
    List<Project> getAllNotDeletedProjects();

    Project findByProjectIdAndIsRemoved(Long id, boolean isDeleted);
}