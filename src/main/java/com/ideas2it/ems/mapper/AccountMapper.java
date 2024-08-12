package com.ideas2it.ems.mapper;

import com.ideas2it.ems.dto.AccountTransactionDto;
import com.ideas2it.ems.model.SalaryAccount;

/**
 * <p>
 *     Converts the json objects according to application operations.
 *     e.g., (dto object -> json object, json object -> dto object)
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class AccountMapper {
    /**
     * <p>
     *     Converts the entity into dto for display and creation purpose.
     * </p>
     *
     * @param account {@link SalaryAccount} to create dto.
     * @return AccountTransactionDto for display and create.
     */
    public static AccountTransactionDto convertToAccountDto(SalaryAccount account) {
        return new AccountTransactionDto(account.getAccountName(), account.getIfscCode());
    }

    /**
     * <p>
     *     Converts dto back to entity for application operation.
     * </p>
     *
     * @param accountDto {@link AccountTransactionDto} for entity conversion.
     * @return SalaryAccount for interior opertion.
     */
    public static SalaryAccount covertDtoToAccount(AccountTransactionDto accountDto) {
        SalaryAccount account = new SalaryAccount(
                accountDto.getAccountName(), accountDto.getIfscCode());
        return account;
    }
}
