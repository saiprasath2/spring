package com.ideas2it.EmployeeManagementSystem.controller;

import com.ideas2it.ems.controller.DepartmentController;
import com.ideas2it.ems.dto.CreationDepartmentDto;
import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.TransactionDepartmentDto;
import com.ideas2it.ems.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDepartment_ShouldReturnCreatedDepartment() {
        CreationDepartmentDto departmentDto = new CreationDepartmentDto();
        TransactionDepartmentDto transactionDto = new TransactionDepartmentDto();
        when(departmentService.addDepartment(any(CreationDepartmentDto.class))).thenReturn(transactionDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.createDepartment(departmentDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionDto, response.getBody());
    }

    @Test
    void getDepartment_ShouldReturnDepartment() {
        Long departmentId = 1L;
        TransactionDepartmentDto departmentDto = new TransactionDepartmentDto();
        when(departmentService.getDepartment(departmentId)).thenReturn(departmentDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.getDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());
    }

    @Test
    void getAllDepartments_ShouldReturnDepartmentList() {
        List<TransactionDepartmentDto> departmentDtos = Arrays.asList(new TransactionDepartmentDto(), new TransactionDepartmentDto());
        when(departmentService.getDepartments()).thenReturn(departmentDtos);
        ResponseEntity<List<TransactionDepartmentDto>> response = departmentController.getAllDepartments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDtos, response.getBody());
    }

    @Test
    void updateDepartment_ShouldReturnUpdatedDepartment() {
        TransactionDepartmentDto departmentDto = new TransactionDepartmentDto();
        when(departmentService.updateDepartment(any(TransactionDepartmentDto.class))).thenReturn(departmentDto);
        ResponseEntity<TransactionDepartmentDto> response = departmentController.updateDepartment(departmentDto);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());
    }

    @Test
    void deleteDepartment_ShouldReturnOk() {
        Long departmentId = 1L;
        doNothing().when(departmentService).deleteDepartment(departmentId);
        ResponseEntity<Void> response = departmentController.deleteDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getEmployeesByDepartmentId_ShouldReturnEmployeeList() {
        Long departmentId = 1L;
        List<DisplayEmployeeDto> employeeDtos = Arrays.asList(new DisplayEmployeeDto(), new DisplayEmployeeDto());
        when(departmentService.getEmployeesOfDepartments(departmentId)).thenReturn(employeeDtos);
        ResponseEntity<List<DisplayEmployeeDto>> response = departmentController.getEmployeesByDepartmentId(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDtos, response.getBody());
    }
}
