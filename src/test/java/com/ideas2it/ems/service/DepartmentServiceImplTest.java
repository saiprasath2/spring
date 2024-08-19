package com.ideas2it.ems.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.exception.EntityAlreadyExistsException;
import com.ideas2it.ems.exception.EntityNotFoundException;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    @Mock
    private DepartmentRepository departmentRepository;

    private CreationDepartmentDto creationDepartmentDto;
    private TransactionDepartmentDto transactionDepartmentDto;
    private DisplayEmployeeDto displayEmployeeDto;
    private Department department;
    Long departmentId;

    @BeforeEach
    public void setup() {
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
    public void testAddDepartmentSuccess() {
        when(departmentRepository.getAllNotDeletedDepartments()).thenReturn(List.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        TransactionDepartmentDto transactionDepartmentDetail = departmentServiceImpl.addDepartment(creationDepartmentDto);
        assertNotNull(transactionDepartmentDetail);
        assertEquals(transactionDepartmentDetail.getName(), department.getDepartmentName());
    }

    @Test
    public void testAddDepartmentAlreadyExists() {
        CreationDepartmentDto creationDepartment = CreationDepartmentDto.builder()
                .name("Render")
                .build();
        when(departmentRepository.getAllNotDeletedDepartments()).thenReturn(List.of(department));
        EntityAlreadyExistsException thrown = assertThrows(EntityAlreadyExistsException.class, ()-> departmentServiceImpl.addDepartment(creationDepartment));
        assertEquals("Department name : " + creationDepartment.getName() + " already exists.", thrown.getMessage());
    }

    @Test
    public void testGetDepartments() {
        when(departmentRepository.getAllNotDeletedDepartments()).thenReturn(List.of(department));
        List<TransactionDepartmentDto> result = departmentServiceImpl.getDepartments();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetDepartment() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(department);
        TransactionDepartmentDto transactionDepartmentDetail = departmentServiceImpl.getDepartment(1L);
        assertNotNull(transactionDepartmentDetail);
        assertEquals(transactionDepartmentDetail.getId(), departmentId);
    }

    @Test
    public void testGetDepartmentNotFound() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> departmentServiceImpl.getDepartment(1L));
        assertEquals("Department with Id : " + departmentId + "not found.", thrown.getMessage());
    }

    @Test
    public void testDeleteDepartment() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(department);
        department.setIsRemoved(true);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        departmentServiceImpl.deleteDepartment(departmentId);
        assertTrue(department.getIsRemoved());
    }

    @Test
    public void testDeleteDepartmentNotFound() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> departmentServiceImpl.deleteDepartment(departmentId));
        assertEquals("Department with Id : " + departmentId + "not found.", thrown.getMessage());
    }

    @Test
    public void testUpdateDepartment() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(department);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        TransactionDepartmentDto transactionDepartmentDetail = departmentServiceImpl.updateDepartment(transactionDepartmentDto);
        assertNotNull(transactionDepartmentDetail);
        assertEquals(transactionDepartmentDetail.getId(), department.getDepartmentId());
    }
    @Test
    public void testUpdateDepartmentNotFound() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> departmentServiceImpl.updateDepartment(transactionDepartmentDto));
        assertEquals("Department with Id : " + departmentId + "not found.", thrown.getMessage());
    }

    @Test
    public void testGetEmployeesOfDepartments() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(department);
        List<DisplayEmployeeDto> displayEmployeeDto = departmentServiceImpl.getEmployeesOfDepartments(departmentId);
        assertEquals(1, displayEmployeeDto.size());
    }

    @Test
    public void testGetEmployeesOfDepartmentsNotFound() {
        when(departmentRepository.findByDepartmentIdAndIsRemoved(departmentId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> departmentServiceImpl.getEmployeesOfDepartments(departmentId));
        assertEquals("Department with Id : " + departmentId + "not found.", thrown.getMessage());
    }
}
