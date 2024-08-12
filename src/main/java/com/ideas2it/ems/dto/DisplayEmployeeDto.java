package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
