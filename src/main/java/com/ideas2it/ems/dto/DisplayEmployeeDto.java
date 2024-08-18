package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Defines blueprint for employee entity for displaying operation,
 *     input should be given in this defined format.
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
public class DisplayEmployeeDto {
    private Long id;

    private String Name;

    private String contact_number;

    private String dateOfBirth;

    private String accountName;

    private String ifscCode;

    private String departmentName;

    private String projects;
}
