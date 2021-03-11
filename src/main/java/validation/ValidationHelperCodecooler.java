package validation;

import exception.EmailFormatException;
import exception.StringLengthFormatException;

import javax.servlet.http.HttpServletRequest;

public class ValidationHelperCodecooler implements ValidationHelper {

    private Validator validator;

    public ValidationHelperCodecooler() {
        this.validator = new Validator();
    }

    @Override
    public boolean callInputsValidation(HttpServletRequest request) {
        boolean isInputValid = true;
        isInputValid = callNameValidation(request, isInputValid, 3, 25);
        isInputValid = callEmailValidation(request, isInputValid, 3, 25);
        isInputValid = callCityValidation(request, isInputValid, 3, 25);

        return isInputValid;
    }

    private boolean callNameValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("person-name"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("name_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callEmailValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateEmail(request.getParameter("person-email"), min, max);
        } catch (EmailFormatException e) {
            request.setAttribute("email_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callCityValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("person-city"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("city_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }
}
