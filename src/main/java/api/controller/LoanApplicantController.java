package api.controller;

import api.converter.IncomeSourceConverter;
import api.converter.LoanApplicantConverter;
import api.converter.RecurringExpenseConverter;
import com.google.gson.Gson;
import db.Database;
import db.dao.LoanApplicantDAO;
import db.model.IncomeSource;
import db.model.LoanApplicant;
import db.model.RecurringExpense;
import defs.dto.IncomeSourceDTO;
import defs.dto.LoanApplicantDTO;
import defs.dto.RecurringExpenseDTO;
import defs.errors.ServerErrorException;
import defs.errors.base.APIError;
import defs.errors.base.APIException;
import defs.other.Success;
import org.hibernate.Session;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class for handling loan applicant API requests.
 */
public class LoanApplicantController {
    private final Gson gson;
    private final LoanApplicantDAO dao;
    private final LoanApplicantConverter converter;

    public LoanApplicantController(Gson gson) {
        this.gson = gson;
        this.dao = new LoanApplicantDAO();
        this.converter = new LoanApplicantConverter();
    }

    /**
     * Initialises all the routes to listen for API requests.
     */
    public void init() {
        // List Loan Applicants
        spark.Spark.get(
            "/loanApplicants",
            this::getAllLoanApplicants,
            gson::toJson);

        // Get Loan Applicant by ID
        spark.Spark.get(
            "/loanApplicants/:id",
            this::getLoanApplicantById,
            gson::toJson);

        // Create Loan Applicant
        spark.Spark.post(
            "/loanApplicants",
            this::saveLoanApplicant,
            gson::toJson);
    }

    /**
     * Retrieves all loan applicants.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A list of all loan applicants or an error body if an error occurred.
     */
    private Object getAllLoanApplicants(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            List<LoanApplicant> loanApplicants = dao.findAll();
            return loanApplicants.stream().map(converter::toDTO).collect(Collectors.toList());
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }

    /**
     * Retrieves a loan applicant by its ID.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A loan applicant or an error body if it was not found or another error occurred.
     */
    private Object getLoanApplicantById(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            Long id = Long.parseLong(request.params(":id"));
            LoanApplicant loanApplicant = dao.find(id);
            if (loanApplicant == null) {
                response.status(404);
                return new APIError("Not found", 404);
            }
            return converter.toDTO(loanApplicant);
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }

    /**
     * Saves a loan applicant entered via POST body.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return An empty body if successful or an error body if the request was malformed or another error occurred.
     */
    private Object saveLoanApplicant(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            Optional<LoanApplicant> savedApplicant = Database.getInstance().performDatabaseAction((Session session) -> {
                // Save the loan applicant
                LoanApplicantDTO loanApplicantDTO = gson.fromJson(request.body(), LoanApplicantDTO.class);
                LoanApplicant loanApplicant = converter.toModel(loanApplicantDTO);
                session.persist(loanApplicant);

                // Save the income sources
                IncomeSourceConverter incConverter = new IncomeSourceConverter();
                List<IncomeSourceDTO> incomeSourceDTOs = loanApplicantDTO.getIncomeSources();
                for (IncomeSourceDTO incomeSourceDTO : incomeSourceDTOs) {
                    IncomeSource incomeSource = incConverter.toModelNewApplicant(incomeSourceDTO, loanApplicant);
                    incomeSource.setLoanApplicant(loanApplicant);
                    session.persist(incomeSource);
                }

                // Save the recurring expenses
                RecurringExpenseConverter expConverter = new RecurringExpenseConverter();
                List<RecurringExpenseDTO> recurringExpenseDTOs = loanApplicantDTO.getRecurringExpenses();
                for (RecurringExpenseDTO recurringExpenseDTO : recurringExpenseDTOs) {
                    RecurringExpense recurringExpense = expConverter.toModelNewApplicant(recurringExpenseDTO, loanApplicant);
                    recurringExpense.setLoanApplicant(loanApplicant);
                    session.persist(recurringExpense);
                }

                return Optional.of(loanApplicant);
            }, true);
            if (savedApplicant.isPresent()) {
                response.status(201);
                return new Success("Loan applicant successfully created with ID " + savedApplicant.get().getId());
            } else {
                throw new ServerErrorException("Error saving loan applicant");
            }
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }
}
