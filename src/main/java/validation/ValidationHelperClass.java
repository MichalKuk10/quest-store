package validation;

import exception.DateFormatException;
import exception.StringLengthFormatException;

import javax.servlet.http.HttpServletRequest;

public class ValidationHelperClass implements ValidationHelper {

    private Validator validator;

    public ValidationHelperClass() {
        this.validator = new Validator();
    }

    @Override
    public boolean callInputsValidation(HttpServletRequest request) {
        boolean isInputValid = true;
        isInputValid = callNameValidation(request, isInputValid, 3, 45);
        isInputValid = callCityValidation(request, isInputValid, 3, 25);
        isInputValid = callStartDateValidation(request, isInputValid, "yyyy-mm-dd");
        isInputValid = callEndDateValidation(request, isInputValid, "yyyy-mm-dd");

        return isInputValid;
    }

    private boolean callNameValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("class-name"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("name_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callCityValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("class-city"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("city_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callStartDateValidation(HttpServletRequest request, boolean isInputValid, String allowedFormat) {
        try {
            validator.validateDate(request.getParameter("class-start-date"), allowedFormat);
        } catch (DateFormatException e) {
            request.setAttribute("start_date_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callEndDateValidation(HttpServletRequest request, boolean isInputValid, String allowedFormat) {
        try {
            validator.validateDate(request.getParameter("class-end-date"), allowedFormat);
        } catch (DateFormatException e) {
            request.setAttribute("end_date_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }
}
