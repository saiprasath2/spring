package com.ideas2it.ems.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "projects")
@Builder
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "project_id")
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long projectId;

    @Column(name = "project_name", unique = true)
    private String projectName;

    @Column(name = "is_deleted")
    private boolean isRemoved;

    @ManyToMany(mappedBy = "project",
            fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Project() {}

    public Project(String projectName) {this.projectName =projectName;}

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
