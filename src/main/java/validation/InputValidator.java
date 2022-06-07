package validation;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Locale;
import java.util.Set;

public class InputValidator {

    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

    public static boolean isValidEmail(String email){
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    public static boolean isValidISOCountry(String countryId) {
        return ISO_COUNTRIES.contains(countryId);
    }
}
