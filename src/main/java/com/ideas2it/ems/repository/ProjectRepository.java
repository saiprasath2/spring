package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.ems.model.Project;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the project.
 * Checks the project along with it's deletion status.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * <p>
     *     Fetches the projects record by checking their deletion
     *     status using boolean value.
     * </p>
     *
     * @return List<Project> for displaying available projects.
     */
    List<Project> findByIsRemovedFalse();

    /**
     * <p>
     *     Fetches the project by checking their deletion status
     *     using boolean value.
     * </p>
     * @param id - Long value to fetch the employee.
     * @param isDeleted - boolean value to check deletion status.
     * @return Project to display the project.
     */
    Project findByProjectIdAndIsRemoved(Long id, boolean isDeleted);
}