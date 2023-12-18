package api.converter;

import api.converter.base.IConverter;
import db.dao.CurrencyDAO;
import db.dao.LoanApplicantDAO;
import db.dao.LoanTypeDAO;
import db.model.Loan;
import defs.dto.LoanDTO;
import org.jetbrains.annotations.NotNull;

/**
 * Class for converting to and from loan DTOs / database entities.
 */
public class LoanConverter implements IConverter<Loan, LoanDTO> {
    /**
     * Converts from a loan database entity to a DTO.
     *
     * @param loan The loan from the database to convert.
     * @return A loan DTO.
     */
    @Override
    public LoanDTO toDTO(Loan loan) {
        return new LoanDTO(
            loan.getId(),
            loan.getLoanApplicant().getId(),
            loan.getLoanType().getLoanName(),
            loan.getValueOfPurchase(),
            loan.getCurrency().getCode()
        );
    }

    /**
     * Converts from a loan DTO to a database entity.
     *
     * @param loanDTO The loan DTO to convert.
     * @return A loan database entity.
     */
    @Override
    public Loan toModel(@NotNull LoanDTO loanDTO) {
        LoanApplicantDAO lAppDAO = new LoanApplicantDAO();
        CurrencyDAO currDAO = new CurrencyDAO();
        LoanTypeDAO lTypeDAO = new LoanTypeDAO();
        return new Loan(
            loanDTO.getId(),
            lAppDAO.find(loanDTO.getApplicantId()),
            lTypeDAO.find(loanDTO.getLoanType()),
            loanDTO.getValueOfPurchase(),
            currDAO.find(loanDTO.getCurrency())
        );
    }
}
