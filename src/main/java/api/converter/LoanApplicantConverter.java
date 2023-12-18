package api.converter;

import api.converter.base.IConverter;
import db.model.IncomeSource;
import db.model.LoanApplicant;
import db.model.RecurringExpense;
import defs.dto.IncomeSourceDTO;
import defs.dto.LoanApplicantDTO;
import defs.dto.RecurringExpenseDTO;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for converting to and from loan applicant DTOs / database entities.
 */
public class LoanApplicantConverter implements IConverter<LoanApplicant, LoanApplicantDTO> {
    /**
     * Converts from a loan applicant database entity to a DTO.
     *
     * @param loanApplicant The loan applicant from the database to convert.
     * @return A loan applicant DTO.
     */
    @Override
    public LoanApplicantDTO toDTO(LoanApplicant loanApplicant) {
        return new LoanApplicantDTO(
            loanApplicant.getId(),
            loanApplicant.getFirstName(),
            loanApplicant.getLastName(),
            loanApplicant.getDateOfBirth(),
            loanApplicant.getMobileNumber(),
            loanApplicant.getEmailAddress(),
            incomeSourcesToDTO(loanApplicant.getIncomeSources()),
            recurringExpensesToDTO(loanApplicant.getRecurringExpenses())
        );
    }

    /**
     * Converts from a loan applicant DTO to a database entity.
     *
     * @param loanApplicantDTO The loan applicant DTO to convert.
     * @return A recurring expense database entity.
     */
    @Override
    public LoanApplicant toModel(@NotNull LoanApplicantDTO loanApplicantDTO) {
        if (loanApplicantDTO.getId() == null) {
            return new LoanApplicant(
                loanApplicantDTO.getFirstName(),
                loanApplicantDTO.getLastName(),
                loanApplicantDTO.getDateOfBirth(),
                loanApplicantDTO.getMobileNumber(),
                loanApplicantDTO.getEmailAddress(),
                null,
                null
            );
        } else {
            return new LoanApplicant(
                loanApplicantDTO.getId(),
                loanApplicantDTO.getFirstName(),
                loanApplicantDTO.getLastName(),
                loanApplicantDTO.getDateOfBirth(),
                loanApplicantDTO.getMobileNumber(),
                loanApplicantDTO.getEmailAddress(),
                incomeSourcesToModel(loanApplicantDTO.getIncomeSources(), loanApplicantDTO.getId()),
                recurringExpensesToModel(loanApplicantDTO.getRecurringExpenses(), loanApplicantDTO.getId())
            );
        }
    }

    /**
     * Converts a list of income sources to DTOs.
     *
     * @param incomeSources Income source database entities.
     * @return A list of income source DTOs.
     */
    private static List<IncomeSourceDTO> incomeSourcesToDTO(@NotNull List<IncomeSource> incomeSources) {
        IncomeSourceConverter converter = new IncomeSourceConverter();
        return incomeSources.stream()
            .map(converter::toDTO)
            .peek(incomeSource -> incomeSource.setApplicantId(null))
            .collect(Collectors.toList());
    }

    /**
     * Converts a list of recurring expenses to DTOs.
     *
     * @param recurringExpenses Recurring expense database entities.
     * @return A list of recurring expense DTOs.
     */
    private static List<RecurringExpenseDTO> recurringExpensesToDTO(@NotNull List<RecurringExpense> recurringExpenses) {
        RecurringExpenseConverter converter = new RecurringExpenseConverter();
        return recurringExpenses.stream()
            .map(converter::toDTO)
            .peek(incomeSource -> incomeSource.setApplicantId(null))
            .collect(Collectors.toList());
    }

    /**
     * Convert a list of income source DTOs to database entities with the given applicant ID.
     *
     * @param incomeSources Income source DTOs.
     * @param applicantId   The applicant ID of a newly-created applicant to associate with them.
     * @return A list of income source database entities.
     */
    private static List<IncomeSource> incomeSourcesToModel(@NotNull List<IncomeSourceDTO> incomeSources,
                                                           Long applicantId) {
        IncomeSourceConverter converter = new IncomeSourceConverter();
        return incomeSources.stream()
            .peek(incomeSource -> incomeSource.setApplicantId(applicantId))
            .map(converter::toModel)
            .collect(Collectors.toList());
    }

    /**
     * Convert a list of recurring expense DTOs to database entities with the given applicant ID.
     *
     * @param recurringExpenses Recurring expense DTOs.
     * @param applicantId       The applicant ID of a newly-created applicant to associate with them.
     * @return A list of recurring expense database entities.
     */
    private static List<RecurringExpense> recurringExpensesToModel(@NotNull List<RecurringExpenseDTO> recurringExpenses,
                                                                   Long applicantId) {
        RecurringExpenseConverter converter = new RecurringExpenseConverter();
        return recurringExpenses.stream()
            .peek(incomeSource -> incomeSource.setApplicantId(applicantId))
            .map(converter::toModel)
            .collect(Collectors.toList());
    }
}
