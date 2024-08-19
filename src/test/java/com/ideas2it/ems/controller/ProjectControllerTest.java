package com.ideas2it.ems.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ideas2it.ems.dto.DisplayEmployeeDto;
import com.ideas2it.ems.dto.ProjectDto;
import com.ideas2it.ems.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;
    private DisplayEmployeeDto displayEmployeeDto;

    @BeforeEach
    void setUp() {

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
    public void testCreateProject() throws Exception {
        ProjectDto projectDto = new ProjectDto(1L, "Project A");
        when(projectService.addProject(any(ProjectDto.class))).thenReturn(projectDto);
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Project A"));
    }

    @Test
    void testGetProject() throws Exception {
        ProjectDto projectDto = new ProjectDto(1L, "Project A");
        when(projectService.getProject(1L)).thenReturn(projectDto);
        mockMvc.perform(get("/api/v1/projects/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Project A"));
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<ProjectDto> projects = List.of(
                new ProjectDto(1L, "Project A"),
                new ProjectDto(2L, "Project B")
        );
        when(projectService.getProjects()).thenReturn(projects);
        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Project A"))
                .andExpect(jsonPath("$[1].name").value("Project B"));
    }

    @Test
    void testUpdateProject() throws Exception {
        ProjectDto projectDto = new ProjectDto(1L, "Updated Project");
        when(projectService.updateProject(any(ProjectDto.class))).thenReturn(projectDto);
        mockMvc.perform(put("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Updated Project"));
    }

    @Test
    void testDeleteProject() throws Exception {
        ProjectDto projectDto = new ProjectDto(1L, "Project to Delete");

        when(projectService.deleteProject(1L)).thenReturn(projectDto);

        mockMvc.perform(put("/api/v1/projects/delete/{id}", 1))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Project to Delete"));
    }

    @Test
    void testGetEmployeesByProjectId() throws Exception {
        List<DisplayEmployeeDto> employees = List.of(displayEmployeeDto);

        when(projectService.getEmployeesOfProjects(1L)).thenReturn(employees);

        mockMvc.perform(get("/api/v1/projects/employees/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
