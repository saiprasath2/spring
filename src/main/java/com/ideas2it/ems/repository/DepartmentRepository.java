package com.ideas2it.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.ems.model.Department;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    String departmentQuery = "from Department where isRemoved = false";

    @Query(departmentQuery)
    List<Department> getAllNotDeletedDepartments();

    Department findByDepartmentIdAndIsRemoved(Long id, boolean isDeleted);
}
