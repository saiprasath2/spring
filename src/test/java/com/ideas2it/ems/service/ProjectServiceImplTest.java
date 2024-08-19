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

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.exception.EntityAlreadyExistsException;
import com.ideas2it.ems.exception.EntityNotFoundException;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.model.SalaryAccount;
import com.ideas2it.ems.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectServiceImpl;

    @Mock
    private ProjectRepository projectRepository;

    private DisplayEmployeeDto displayEmployeeDto;
    private ProjectDto projectDto;
    private Project project;
    Long projectId;

    @BeforeEach
    public void setup() {
        projectId = 2L;
        projectDto = ProjectDto.builder()
                .id(2L)
                .name("Test")
                .build();
        project = Project.builder()
                .projectId(2L)
                .projectName("Render")
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
                                .projectName("Rend")
                                .build()))
                        .build()))
                .build();

        displayEmployeeDto = DisplayEmployeeDto.builder()
                .id(1L)
                .Name("Sai")
                .departmentName("Render")
                .dateOfBirth("2002-09-09")
                .projects("Rend")
                .accountName("HDFC67645")
                .ifscCode("HDFC9879")
                .build();
    }

    @Test
    public void testAddProjectSuccess() {
        when(projectRepository.findByIsRemovedFalse()).thenReturn(List.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        ProjectDto projectDetail = projectServiceImpl.addProject(projectDto);
        assertNotNull(projectDetail);
        assertEquals(projectDetail.getId(), projectDto.getId());
    }

    @Test
    public void testAddProjectAlreadyExists() {
        ProjectDto creationProject = ProjectDto.builder()
                .name("Render")
                .build();
        when(projectRepository.findByIsRemovedFalse()).thenReturn(List.of(project));
        EntityAlreadyExistsException thrown = assertThrows(EntityAlreadyExistsException.class, ()-> projectServiceImpl.addProject(creationProject));
        assertEquals("Project name : " + creationProject.getName() + " already exists.", thrown.getMessage());
    }

    @Test
    public void testGetProjects() {
        when(projectRepository.findByIsRemovedFalse()).thenReturn(List.of(project));
        List<ProjectDto> result = projectServiceImpl.getProjects();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetProject() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(project);
        ProjectDto projectDetail = projectServiceImpl.getProject(projectId);
        assertNotNull(projectDetail);
        assertEquals(projectDetail.getId(), projectDto.getId());
    }

    @Test
    public void testGetProjectNotFound() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> projectServiceImpl.getProject(projectId));
        assertEquals("Project with Id : " + projectId + "not found.", thrown.getMessage());
    }

    @Test
    public void testDeleteProject() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(project);
        project.setIsRemoved(true);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        projectServiceImpl.deleteProject(projectId);
        assertTrue(project.getIsRemoved());
    }

    @Test
    public void testDeleteProjectNotFound() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> projectServiceImpl.deleteProject(projectId));
        assertEquals("Project with Id : " + projectId + "not found.", thrown.getMessage());
    }

    @Test
    public void testUpdateProject() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        ProjectDto projectDetail = projectServiceImpl.updateProject(projectDto);
        assertNotNull(projectDetail);
        assertEquals(projectDetail.getId(), projectId);
    }
    @Test
    public void testUpdateProjectNotFound() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> projectServiceImpl.updateProject(projectDto));
        assertEquals("Project with Id : " + projectId + "not found.", thrown.getMessage());
    }

    @Test
    public void testGetEmployeesOfProject() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(project);
        List<DisplayEmployeeDto> displayEmployeeDto = projectServiceImpl.getEmployeesOfProjects(projectId);
        assertEquals(1, displayEmployeeDto.size());
    }

    @Test
    public void testGetEmployeesOfProjectNotFound() {
        when(projectRepository.findByProjectIdAndIsRemoved(projectId, false)).thenReturn(null);
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, ()-> projectServiceImpl.getEmployeesOfProjects(projectId));
        assertEquals("Project with Id : " + projectId + "not found.", thrown.getMessage());
    }
}
