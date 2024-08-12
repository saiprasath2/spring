package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.ems.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByIsRemovedFalse();

    Project findByProjectIdAndIsRemoved(Long id, boolean isDeleted);
}