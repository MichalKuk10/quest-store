package validation;

import javax.servlet.http.HttpServletRequest;

public interface ValidationHelper {
    boolean callInputsValidation(HttpServletRequest request);
}
