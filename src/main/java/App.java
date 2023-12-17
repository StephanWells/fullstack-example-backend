import db.Database;
import db.dao.LoanApplicantDAO;
import db.model.LoanApplicant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Props;

import java.time.LocalDate;
import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        Props.initialise();
        Database.initialise();
        LoanApplicant loanApplicant = new LoanApplicant(
            200L,
            "Stephan",
            "Wells",
            LocalDate.of(1995, 5, 15),
            "+35679123456",
            "stephanwells@hotmail.com"
        );
        LoanApplicantDAO dao = new LoanApplicantDAO();
        dao.update(loanApplicant);
        List<LoanApplicant> applicants = dao.findAll();
    }
}
