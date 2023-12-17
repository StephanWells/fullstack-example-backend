package util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;

/**
 * A utility class with static methods for validating fields.
 */
public class Validation {
    /**
     * Validates an email.
     *
     * @param email The email to validate.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        boolean isValid = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Validates a mobile number.
     *
     * @param mobileNumber The mobile number to validate.
     * @return True if the mobile number is valid, false otherwise.
     */
    public static boolean isValidMobileNumber(String mobileNumber) {
        boolean isValid = true;
        if (!mobileNumber.matches("^[\\d+]+([\\s\\-]*\\d+){4,}$")) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Validates a date of birth.
     *
     * @param dateOfBirth The date of birth to validate.
     * @return True if the date of birth is valid, false otherwise.
     */
    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        boolean isValid = true;
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(18))) { // If the age is under 18
            isValid = false;
        } else if (dateOfBirth.isBefore(LocalDate.now().minusYears(70))) { // If the age is over 70
            isValid = false;
        } else if (dateOfBirth.isAfter(LocalDate.now())) { // If the age is in the future
            isValid = false;
        }

        return isValid;
    }

    /**
     * Validates a monetary value.
     *
     * @param monetaryValue The monetary value to validate.
     * @return True if the monetary value is valid, false otherwise.
     */
    public static boolean isValidMonetaryValue(double monetaryValue) {
        boolean isValid = true;
        if (monetaryValue <= 0) {
            isValid = false;
        }

        return isValid;
    }
}
