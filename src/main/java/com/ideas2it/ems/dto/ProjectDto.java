package com.ideas2it.ems.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

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
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;

    @NotBlank
    @Size(min = 6, max = 15, message = "Project name should contain 6 to 15 letters")
    private String name;
}