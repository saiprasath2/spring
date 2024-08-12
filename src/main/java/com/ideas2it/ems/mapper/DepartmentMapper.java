package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.model.Department;

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
    public static CreationDepartmentDto convertToCreateDto(Department department) {
        return new CreationDepartmentDto(department.getDepartmentName());
    }

    public static Department convertCreateToEntity(CreationDepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getName());
        return department;
    }

    public static TransactionDepartmentDto convertToTransactionDto(Department department) {
        return new TransactionDepartmentDto(department.getDepartmentId(), department.getDepartmentName());
    }

    public static Department convertTransactionToEntity(TransactionDepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentId(departmentDto.getId());
        department.setDepartmentName(departmentDto.getName());
        return department;
    }
}
