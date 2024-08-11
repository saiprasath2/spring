package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplaySalaryAccountDto {
    private Long id;

    private String AccountNumber;

    private String IfscCode;
}

