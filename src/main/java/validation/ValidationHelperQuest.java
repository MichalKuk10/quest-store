package validation;

import exception.StringLengthFormatException;
import exception.TypeFormatException;
import exception.ValueFormatException;

import javax.servlet.http.HttpServletRequest;

public class ValidationHelperQuest implements ValidationHelper {
    private final Validator validator;

    public ValidationHelperQuest() {
        this.validator = new Validator();
    }

    @Override
    public boolean callInputsValidation(HttpServletRequest request) {
        boolean isInputValid = true;
        isInputValid = callNameValidation(request, isInputValid, 3, 50);
        isInputValid = callDescriptionValidation(request, isInputValid, 3, 100);
        isInputValid = callValueValidation(request, isInputValid, 0, 1000000);
        isInputValid = callTypeValidation(request, isInputValid, "BASIC", "EXTRA");

        return isInputValid;
    }

    private boolean callNameValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("quest-name"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("name_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callDescriptionValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateStringLength(request.getParameter("quest-description"), min, max);
        } catch (StringLengthFormatException e) {
            request.setAttribute("description_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callValueValidation(HttpServletRequest request, boolean isInputValid, int min, int max) {
        try {
            validator.validateValue(request.getParameter("quest-value"), min, max);
        } catch (ValueFormatException e) {
            request.setAttribute("value_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }

    private boolean callTypeValidation(HttpServletRequest request, boolean isInputValid, String type1, String type2) {
        try {
            validator.validateType(request.getParameter("quest-type"), type1, type2);
        } catch (TypeFormatException e) {
            request.setAttribute("type_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }
}
