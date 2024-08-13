package com.ideas2it.ems.dto;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * <p>
 *     Defines the format of entity object at web server and application.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreationDepartmentDto {
    @NotBlank
    @Size(min = 2, max = 15, message = "Name should contain 2 to 15 letters.")
    private String name;
}
