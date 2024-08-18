package com.ideas2it.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Defines blueprint for project entity for creation, updation and displaying
 *     operation, input should be given in this defined format.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;

    @NotBlank(message = "Name should not be empty.")
    @Size(min = 6, max = 15, message = "Project name should contain 6 to 15 letters")
    private String name;
}