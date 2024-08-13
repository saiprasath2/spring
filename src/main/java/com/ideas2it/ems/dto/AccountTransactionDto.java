package com.ideas2it.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Defines blueprint for SalaryAccount entity for cretion,updation and displaying
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
public class AccountTransactionDto {
    private String accountName;

    private String ifscCode;
}
