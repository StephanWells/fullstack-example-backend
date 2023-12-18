package api.controller;

import api.converter.CurrencyConverter;
import com.google.gson.Gson;
import db.dao.CurrencyDAO;
import db.model.Currency;
import defs.errors.base.APIError;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for handling currency API requests.
 */
public class CurrencyController {
    private final Gson gson;
    private final CurrencyDAO dao;
    private final CurrencyConverter converter;

    public CurrencyController(Gson gson) {
        this.gson = gson;
        this.dao = new CurrencyDAO();
        this.converter = new CurrencyConverter();
    }

    /**
     * Initialises all the routes to listen for API requests.
     */
    public void init() {
        // List Currencies
        spark.Spark.get(
            "/currencies",
            this::getAllCurrencies,
            gson::toJson);
    }

    /**
     * Retrieves all currencies.
     *
     * @param request  The API request.
     * @param response The API response.
     * @return A list of all currencies or an error body if an error occurred.
     */
    private Object getAllCurrencies(Request request, Response response) {
        response.header("content-type", "application/json");
        try {
            List<Currency> currencies = dao.findAll();
            return currencies.stream().map(converter::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            response.status(500);
            return new APIError("Server error", 500);
        }
    }
}
