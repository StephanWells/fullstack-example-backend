package api.controller;

import api.converter.LoanConverter;
import com.google.gson.Gson;
import db.dao.LoanDAO;
import db.model.Loan;
import defs.dto.LoanDTO;
import defs.errors.base.APIError;
import defs.errors.base.APIException;
import defs.other.Success;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for handling loan API requests.
 */
public class LoanController {
    private final Gson gson;
    private final LoanDAO dao;
    private final LoanConverter converter;

    public LoanController(Gson gson) {
        this.gson = gson;
        this.dao = new LoanDAO();
        this.converter = new LoanConverter();
    }

    /**
     * Initialises all the routes to listen for API requests.
     */
    public void init() {
        // List Loans
        spark.Spark.get(
            "/loans",
            this::getAllLoans,
            gson::toJson);

        // Get Loan by ID
        spark.Spark.get(
            "/loans/:id",
            this::getLoanById,
            gson::toJson);

        // Create Loan
        spark.Spark.post(
            "/loans",
            this::saveLoan,
            gson::toJson);
    }

    /**
     * Retrieves all loans.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A list of all loans or an error body if an error occurred.
     */
    private Object getAllLoans(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            List<Loan> loans = dao.findAll();
            return loans.stream().map(converter::toDTO).collect(Collectors.toList());
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A loan or an error body if it was not found or another error occurred.
     */
    private Object getLoanById(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            Long id = Long.parseLong(request.params(":id"));
            Loan loan = dao.find(id);
            if (loan == null) {
                response.status(404);
                return new APIError("Not found", 404);
            }
            return converter.toDTO(loan);
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }

    /**
     * Saves a loan entered via POST body.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return An empty body if successful or an error body if the request was malformed or another error occurred.
     */
    private Object saveLoan(Request request, Response response) {

        try {
            response.header("content-type", "application/json");
            LoanDTO loanDTO = gson.fromJson(request.body(), LoanDTO.class);
            Loan loan = converter.toModel(loanDTO);
            dao.save(loan);

            response.status(201);
            return new Success("Loan successfully created with ID " + loan.getId());
        } catch (APIException e) {
            response.status(e.getApiError().getStatusCode());
            return e.getApiError();
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }
}
