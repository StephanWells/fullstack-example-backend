package api.converter;

import api.converter.base.IConverter;
import db.dao.CurrencyDAO;
import db.dao.LoanApplicantDAO;
import db.model.LoanApplicant;
import db.model.RecurringExpense;
import defs.dto.RecurringExpenseDTO;
import org.jetbrains.annotations.NotNull;

/**
 * Class for converting to and from recurring expense DTOs / database entities.
 */
public class RecurringExpenseConverter implements IConverter<RecurringExpense, RecurringExpenseDTO> {
    /**
     * Converts from a recurring expense database entity to a DTO.
     *
     * @param recurringExpense The recurring expense from the database to convert.
     * @return A recurring expense DTO.
     */
    @Override
    public RecurringExpenseDTO toDTO(@NotNull RecurringExpense recurringExpense) {
        return new RecurringExpenseDTO(
            recurringExpense.getId(),
            recurringExpense.getLoanApplicant().getId(),
            recurringExpense.getExpenseType(),
            recurringExpense.getMonthlyExpense(),
            recurringExpense.getCurrency().getCode());
    }

    /**
     * Converts from a recurring expense DTO to a database entity.
     *
     * @param recurringExpenseDTO The recurring expense DTO to convert.
     * @return A recurring expense database entity.
     */
    @Override
    public RecurringExpense toModel(@NotNull RecurringExpenseDTO recurringExpenseDTO) {
        LoanApplicantDAO dao = new LoanApplicantDAO();
        CurrencyDAO currDAO = new CurrencyDAO();
        return new RecurringExpense(
            recurringExpenseDTO.getId(),
            dao.find(recurringExpenseDTO.getApplicantId()),
            recurringExpenseDTO.getExpenseType(),
            recurringExpenseDTO.getMonthlyExpense(),
            currDAO.find(recurringExpenseDTO.getCurrency()));
    }

    /**
     * Converts from a recurring expense DTO to a database entity, using a newly-created loan applicant.
     *
     * @param recurringExpenseDTO The recurring expense DTO to convert.
     * @param loanApplicant       The new loan applicant that was just created.
     * @return A recurring expense database entity with association with the newly-created loan applicant.
     */
    public RecurringExpense toModelNewApplicant(@NotNull RecurringExpenseDTO recurringExpenseDTO,
                                                LoanApplicant loanApplicant) {
        CurrencyDAO currDAO = new CurrencyDAO();
        return new RecurringExpense(
            recurringExpenseDTO.getId(),
            loanApplicant,
            recurringExpenseDTO.getExpenseType(),
            recurringExpenseDTO.getMonthlyExpense(),
            currDAO.find(recurringExpenseDTO.getCurrency()));
    }
}
