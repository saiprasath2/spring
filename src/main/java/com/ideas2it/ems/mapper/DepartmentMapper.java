package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.EmployeeCreationDto;
import com.ideas2it.ems.dto.EmployeeUpdationDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class DepartmentMapper {

    /**
     * <p>
     *     Converts creation format of dto into Entity
     *     for internal updation.
     * </p>
     * @param departmentDto {@link CreationDepartmentDto} to convert the dto.
     * @return Department to update.
     */
    public static Department convertCreateToEntity(CreationDepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getName());
        return department;
    }

    /**
     * <p>
     *     Converts the entity to displayable dto format.
     * </p>
     * @param department {@link Department} for conversion into dto.
     * @return TransactionDepartmentDto to display.
     */
    public static TransactionDepartmentDto convertToTransactionDto(Department department) {
        return new TransactionDepartmentDto(department.getDepartmentId(), department.getDepartmentName());
    }

    /**
     * <p>
     *     Converts displaying format of dto into Entity
     *     for internal operation.
     * </p>
     * @param departmentDto {@link TransactionDepartmentDto} to convert the dto.
     * @return Department to update.
     */
    public static Department convertTransactionToEntity(TransactionDepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentId(departmentDto.getId());
        department.setDepartmentName(departmentDto.getName());
        return department;
    }
}
