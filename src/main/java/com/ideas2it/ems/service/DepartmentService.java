package com.ideas2it.ems.service;

import java.util.List;
import java.util.Set;

import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;

import org.springframework.stereotype.Service;

/** 
 * <p>
 * Passes value for insertion,deletion and retrieval on Department.
 * </p>
 *
 * @author   Saiprasath 
 * @version  1.5
 */
@Service
public interface DepartmentService {

    /**
     * <p>
     * passes the value for insertion into the collection.
     * </p>
     *
     * @param department  String value to set department Name.
     * @return Department value to indicate insertion status.
     */
    Department addOrUpdateDepartment(Department department);

    /**
     * <p>
     * Calls retrieveEmployeeDepartments to get the department list.
     * </p>
     *
     * @return List<> value to display department list.
     */
    List<Department> getDepartments();

    /**
     * <p>
     * Calls the getDepartment to fetch the department and returns the department.
     * </p>
     *
     * @param departmentId  Long value to display the department.
     * @return Department value to display the department.
     */
    Department getDepartment(Long departmentId);

    /**
     * <p>
     *     Removes the department from user view by changing boolean value.
     * </p>
     * @param departmentId to remove department.
     * @return Department value to acknowledge
     */
    Department deleteDepartment(Long departmentId);

    /**
     * <p>
     * Returns employee record under the specific departmentt.
     * </p>
     *
     * @param departmentId int value to get the project.
     * @return Set<> value to list the employee.
     */
    Set<Employee> getEmployeesOfDepartments(Long departmentId);
}