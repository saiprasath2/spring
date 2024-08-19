package com.ideas2it.ems.controller;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    private CreationDepartmentDto creationDepartmentDto;
    private TransactionDepartmentDto transactionDepartmentDto;
    private DisplayEmployeeDto displayEmployeeDto;
    private Department department;
    Long departmentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        departmentId = 1L;
        creationDepartmentDto = CreationDepartmentDto.builder()
                .name("Tester")
                .build();

        transactionDepartmentDto = TransactionDepartmentDto.builder()
                .id(1L)
                .name("Tester")
                .build();

        department = Department.builder()
                .departmentId(1L)
                .departmentName("Render")
                .isRemoved(false)
                .employees(Set.of(Employee.builder()
                        .employeeId(1L)
                        .employeeName("Sai")
                        .employeeDob(LocalDate.of(2002,9,9))
                        .contactNumber("9098979098")
                        .salaryAccount(SalaryAccount.builder()
                                .accountId(1L)
                                .accountName("HDFC67645")
                                .ifscCode("HDFC9879")
                                .build())
                        .department(Department.builder()
                                .departmentId(1L)
                                .departmentName("Render")
                                .build())
                        .project(Set.of(Project.builder()
                                .projectId(1L)
                                .projectName("Roche1")
                                .build()))
                        .build()))
                .build();

        displayEmployeeDto = DisplayEmployeeDto.builder()
                .id(1L)
                .Name("Sai")
                .departmentName("Render")
                .dateOfBirth("2002-09-09")
                .projects("Roche1")
                .accountName("HDFC67645")
                .ifscCode("HDFC9879")
                .build();
    }

    @Test
    void createDepartmentTest() {
        when(departmentService.addDepartment(any(CreationDepartmentDto.class))).thenReturn(transactionDepartmentDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.createDepartment(creationDepartmentDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionDepartmentDto, response.getBody());
    }

    @Test
    void getDepartmentTest() {
        when(departmentService.getDepartment(departmentId)).thenReturn(transactionDepartmentDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.getDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionDepartmentDto, response.getBody());
    }

    @Test
    void getAllDepartmentsTest() {
        when(departmentService.getDepartments()).thenReturn(List.of(transactionDepartmentDto));
        ResponseEntity<List<TransactionDepartmentDto>> response = departmentController.getAllDepartments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateDepartmentTest() {
        TransactionDepartmentDto departmentDto = new TransactionDepartmentDto();
        when(departmentService.updateDepartment(any(TransactionDepartmentDto.class))).thenReturn(departmentDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.updateDepartment(departmentDto);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());
    }

    @Test
    void deleteDepartmentTest() {
        doNothing().when(departmentService).deleteDepartment(departmentId);
        ResponseEntity<Void> response = departmentController.deleteDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getEmployeesByDepartmentIdTest() {
        when(departmentService.getEmployeesOfDepartments(departmentId)).thenReturn(List.of(displayEmployeeDto));
        ResponseEntity<List<DisplayEmployeeDto>> response = departmentController.getEmployeesByDepartmentId(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
