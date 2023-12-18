package api.converter;

import api.converter.base.IConverter;
import db.dao.CurrencyDAO;
import db.dao.LoanApplicantDAO;
import db.model.IncomeSource;
import db.model.LoanApplicant;
import defs.dto.IncomeSourceDTO;
import org.jetbrains.annotations.NotNull;

/**
 * Class for converting to and from income source DTOs / database entities.
 */
public class IncomeSourceConverter implements IConverter<IncomeSource, IncomeSourceDTO> {
    /**
     * Converts from an income source database entity to a DTO.
     *
     * @param incomeSource The income source from the database to convert.
     * @return An income source DTO.
     */
    @Override
    public IncomeSourceDTO toDTO(@NotNull IncomeSource incomeSource) {
        return new IncomeSourceDTO(
            incomeSource.getId(),
            incomeSource.getLoanApplicant().getId(),
            incomeSource.getIncomeType(),
            incomeSource.getMonthlyIncome(),
            incomeSource.getCurrency().getCode());
    }

    /**
     * Converts from an income source DTO to a database entity.
     *
     * @param incomeSourceDTO The income source DTO to convert.
     * @return An income source database entity.
     */
    @Override
    public IncomeSource toModel(@NotNull IncomeSourceDTO incomeSourceDTO) {
        LoanApplicantDAO lAppDAO = new LoanApplicantDAO();
        CurrencyDAO currDAO = new CurrencyDAO();
        return new IncomeSource(
            incomeSourceDTO.getId(),
            lAppDAO.find(incomeSourceDTO.getApplicantId()),
            incomeSourceDTO.getIncomeType(),
            incomeSourceDTO.getMonthlyIncome(),
            currDAO.find(incomeSourceDTO.getCurrency()));
    }

    /**
     * Converts from an income source DTO to a database entity, using a newly-created loan applicant.
     *
     * @param incomeSourceDTO The income source DTO to convert.
     * @param loanApplicant   The new loan applicant that was just created.
     * @return A recurring expense database entity with association with the newly-created loan applicant.
     */
    public IncomeSource toModelNewApplicant(@NotNull IncomeSourceDTO incomeSourceDTO, LoanApplicant loanApplicant) {
        CurrencyDAO currDAO = new CurrencyDAO();
        return new IncomeSource(
            incomeSourceDTO.getId(),
            loanApplicant,
            incomeSourceDTO.getIncomeType(),
            incomeSourceDTO.getMonthlyIncome(),
            currDAO.find(incomeSourceDTO.getCurrency()));
    }
}
