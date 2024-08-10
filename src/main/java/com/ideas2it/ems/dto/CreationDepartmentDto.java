package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;
}
