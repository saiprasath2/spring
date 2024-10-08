package com.ideas2it.ems.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Set;

/**
 * <p> 
 * Represents blueprLongfor the department datatype.
 * Contains details of employee such as Id, name.
 * Includes collections of employee alotted to a department.
 * </p>
 * 
 * @author Saiprasath
 * @version 1.4
 */
@Entity
@Table(name = "departments")
@AllArgsConstructor
@Builder
public class Department {
    
    @Id
    @Column(name = "department_id")
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Long departmentId;

    @Column(name = "department_name", unique = true)
    private String departmentName;

    @Column(name = "is_deleted")
    private boolean isRemoved;

    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    public Set<Employee> employees;

    public Department() {}

    public Department(Long departmentId) {this.departmentId = departmentId;}

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName; 
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void SetEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}