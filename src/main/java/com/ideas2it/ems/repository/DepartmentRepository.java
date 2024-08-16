package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.ems.model.Department;

/**
 * <p>
 * Inserts, deletes, updates and fetches data of the department.
 * Checks the department along with it's deletion status.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    String departmentQuery = "from Department where isRemoved = false";

    /**
     * <p>
     *     Fetches the departments record by checking their deletion
     *     status using the query.
     * </p>
     *
     * @return List<Project> for displaying available projects.
     */
    @Query(departmentQuery)
    List<Department> getAllNotDeletedDepartments();

    /**
     * <p>
     *     Fetches the department by checking their deletion status
     *     using boolean value.
     * </p>
     * @param id - Long value to fetch the employee.
     * @param isDeleted - boolean value to check deletion status.
     * @return Department to display the project.
     */
    Department findByDepartmentIdAndIsRemoved(Long id, boolean isDeleted);
}
