package com.ideas2it.ems.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.ideas2it.ems.util.Util;

/**
 * <p> Represents blueprint for the Employee datatype.
 * Contains details of employee such as Id, name, age.
 * Includes collections of project and department alotted to employees.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@Entity
@Table(name = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id")
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long employeeId;

    @Column(name = "name", nullable = false, length = 36)
    private String employeeName;

    @Column(name = "employee_dob", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate employeeDob;

    @Column(name = "contact_number", nullable = false, length = 10)
    private String contactNumber;

    @Column(name = "is_deleted")
    private boolean isRemoved;

    @OneToOne(targetEntity = SalaryAccount.class,
              cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private SalaryAccount salaryAccount;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "association_employee_project",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> project;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(LocalDate employeeDob) {
        this.employeeDob = employeeDob;
    }

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
    /**
     * Returns set of projects assigned for an employee to getAllprojects.
     *
     * @return Set<> value to get project list.
     */
    public Set<Project> getProject() {
        return project;
    }

    public void setProject(Set<Project> project) {
        this.project = project;
    }

    public String getAllProjects() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Project project : getProject()) {
            stringBuilder.append(project.getProjectName() + ",");
        }
        return stringBuilder.toString();
    }

    public SalaryAccount getSalaryAccount() {
        return salaryAccount;
    }

    public void setSalaryAccount(SalaryAccount salaryAccount) {
        this.salaryAccount = salaryAccount;
    }

    /**
     * Calculates the current age of the employee from
     * dateOfBirth value passed.
     *
     * @return String value of current age of the employee.
     */
    public String getAge() {
            return Util.calculateAge(getEmployeeDob());
    }
}