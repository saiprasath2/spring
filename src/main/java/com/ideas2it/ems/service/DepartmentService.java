package com.ideas2it.ems.service;

import java.util.List;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import org.springframework.stereotype.Service;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.model.Department;

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
     *    passes the value for insertion into the collection.
     * </p>
     *
     * @param departmentDto {@link CreationDepartmentDto}  String value to set department Name.
     * @return TransactionDepartmentDto value to indicate insertion status.
     */
    TransactionDepartmentDto addDepartment(CreationDepartmentDto departmentDto);

    /**
     * <p>
     *    Retrieves department records available.
     * </p>
     *
     * @return List<TransactionDepartmentDto> value to display department list.
     */
    List<TransactionDepartmentDto> getDepartments();

    /**
     * <p>
     *    Fetches the department and returns the department.
     * </p>
     *
     * @param departmentId - Long value to display the department.
     * @return TransactionDepartmentDto value to display the department.
     */
    TransactionDepartmentDto getDepartment(Long departmentId);

    /**
     * <p>
     *    Removes the department from user view by changing boolean value.
     * </p>
     *
     * @param departmentId - Long value to remove department.
     */
    void deleteDepartment(Long departmentId);

    /**
     * <p>
     *    Updates the department name.
     * </p>
     *
     * @param departmentDto - {@link TransactionDepartmentDto} value to update department.
     * @return TransactionDepartmentDto to acknowledge.
     */
    TransactionDepartmentDto updateDepartment(TransactionDepartmentDto departmentDto);
    /**
     * <p>
     *    Returns employee record under the specific department.
     * </p>
     *
     * @param departmentId - Long value to get the project.
     * @return List<DisplayEmployeeDto> value to list the employee.
     */
    List<DisplayEmployeeDto> getEmployeesOfDepartments(Long departmentId);
}