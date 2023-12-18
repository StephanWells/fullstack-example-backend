package api.converter;

import api.converter.base.IConverter;
import db.model.LoanType;
import defs.dto.LoanTypeDTO;
import defs.enums.AcceptedLoanType;
import defs.errors.LoanTypeNotFoundException;

import java.util.Arrays;

/**
 * Class for converting to and from loan type name / database entities.
 */
public class LoanTypeConverter implements IConverter<LoanType, LoanTypeDTO> {
    /**
     * Converts from an accepted loan type database entity to a loan type DTO.
     *
     * @param loanType The loan type from the database to convert.
     * @return The loan type DTO.
     */
    public LoanTypeDTO toDTO(LoanType loanType) {
        if (loanType == null) {
            return null;
        }
        return new LoanTypeDTO(loanType.getLoanName(), loanType.getLoanPurchase());
    }

    /**
     * Converts from a loan type name to a database entity.
     *
     * @param loanTypeDTO The accepted loan type name.
     * @return A loan type database entity.
     */
    public LoanType toModel(LoanTypeDTO loanTypeDTO) {
        AcceptedLoanType loanType =
            Arrays.stream(AcceptedLoanType.values())
                .filter(lType -> lType.loanName.equals(loanTypeDTO.getLoanName()))
                .findFirst()
                .orElse(null);

        if (loanType == null) {
            throw new LoanTypeNotFoundException("Loan type not found", loanTypeDTO.getLoanName());
        }
        return loanType.toEntity();
    }
}