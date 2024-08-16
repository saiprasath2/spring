package com.ideas2it.ems.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;



/**
 * <p>
 *     Defines blueprint for employee entity for updation operation,
 *     input should be given in this defined format.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdationDto {
    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20, message = "Name should contain 3 to 20 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should contain only letters.")
    private String name;

    @NotBlank
    @Size(min = 10, max = 10, message = "Number should contain 10 digits.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}$")
    private String contact_number;

    @NotBlank
    @Past(message = "Give the DOB in past.")
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(min = 11, max = 16)
    private String accountName;

    @NotBlank
    @Size(min = 7, max = 10)
    private String ifscCode;

    @NotBlank
    private Long departmentId;
}
