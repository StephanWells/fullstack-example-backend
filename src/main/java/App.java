import api.controller.CurrencyController;
import api.controller.LoanApplicantController;
import api.controller.LoanController;
import api.controller.LoanTypeController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Spark;
import util.config.Props;
import util.typeadapters.LocalDateTypeAdapter;

import java.time.LocalDate;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    /**
     * Main class - the app starts here.
     *
     * @param args Arguments to pass in when running the application.
     */
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        Props.initialise();
        Database.initialise();
        startApi();
    }

    /**
     * Starts the API to listen for requests.
     */
    public static void startApi() {
        Spark.ipAddress(Props.getApiHost());
        Spark.port(Props.getApiPort());
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

        // Loan Applicants
        LoanApplicantController loanApplicantController = new LoanApplicantController(gson);
        loanApplicantController.init();

        // Loans
        LoanController loanController = new LoanController(gson);
        loanController.init();

        // Currencies
        CurrencyController currencyController = new CurrencyController(gson);
        currencyController.init();

        // Loan Types
        LoanTypeController loanTypeController = new LoanTypeController(gson);
        loanTypeController.init();

        Spark.awaitInitialization();
    }
}
