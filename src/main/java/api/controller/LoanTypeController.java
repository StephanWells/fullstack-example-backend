package api.controller;

import api.converter.LoanTypeConverter;
import com.google.gson.Gson;
import db.dao.LoanTypeDAO;
import db.model.LoanType;
import defs.errors.base.APIError;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for handling currency API requests.
 */
public class LoanTypeController {
    private final Gson gson;
    private final LoanTypeDAO dao;
    private final LoanTypeConverter converter;

    public LoanTypeController(Gson gson) {
        this.gson = gson;
        this.dao = new LoanTypeDAO();
        this.converter = new LoanTypeConverter();
    }

    /**
     * Initialises all the routes to listen for API requests.
     */
    public void init() {
        // List Currencies
        spark.Spark.get(
            "/loanTypes",
            this::getAllLoanTypes,
            gson::toJson);
    }

    /**
     * Retrieves all loan types.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A list of all loan types or an error body if an error occurred.
     */
    private Object getAllLoanTypes(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            List<LoanType> loanTypes = dao.findAll();
            return loanTypes.stream().map(converter::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }
}
