package com.ideas2it.ems.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

/**
 * <p>
 *     Defines blueprint for employee entity for creation operation,
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
public class EmployeeCreationDto {
    @NotBlank
    @Size(min = 3, max = 20, message = "Name should contain 3 to 20 letters.")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should contain only letters.")
    private String Name;

    @NotBlank
    @Size(min = 10, max = 10, message = "Number should contain 10 digits.")
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}$", message = "Contact Number should contain only numbers.")
    private String contact_number;

    @NonNull
    @Past(message = "Give the DOB in past.")
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(min = 11, max = 16)
    private String accountName;

    @NotBlank
    @Size(min = 7, max = 10)
    private String ifscCode;

    @NonNull
    private Long departmentId;
}
