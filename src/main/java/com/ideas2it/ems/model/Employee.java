package com.ideas2it.ems.model;

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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "")
    private SalaryAccount salaryAccount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_sports_activity",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_id"))
    private Set<SportsActivity> sportsActivities = new HashSet<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDob(LocalDate  dob) {
        this.dob = dob;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Set<SportsActivity> getSportsActivities() {
        return sportsActivities != null ? sportsActivities : new HashSet<>();
    }

    public void setSportsActivities(Set<SportsActivity> sportsActivities) {
        this.sportsActivities = sportsActivities;
    }

}