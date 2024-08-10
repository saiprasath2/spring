package com.ideas2it.ems.service;

import java.util.List;

import com.ideas2it.ems.model.Project;

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
     * passes the value for insertion into the collection.
     * </p>
     *
     * @param project  String value to set Project Name.
     * @return Project value to indicate insertion status.
     */
    public Project addOrUpdateProject(Project project);

    /**
     * <p>
     * Calls retrieveEmployeeProjects to get the Project list.
     * </p>
     *
     * @return List<> value to display Project list.
     */
    public List<Project> getProjects();

    /**
     * <p>
     * Calls the getProject to fetch the Project and returns the Project.
     * </p>
     *
     * @param projectId  Long value to display the Project.
     * @return Project value to display the Project.
     */
    public Project getProject(Long projectId);

    /**
     * <p>
     *     Removes the Project from user view by changing boolean value.
     * </p>
     * @param projectId to remove Project.
     * @return Project value to acknowledge.
     */
    public Project deleteProject(Long projectId);
}
