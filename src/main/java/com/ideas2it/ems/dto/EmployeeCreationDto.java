package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String Name;

    private String contact_number;

    private String dateOfBirth;

    private String accountName;

    private String ifscCode;

    private Long departmentId;
}
