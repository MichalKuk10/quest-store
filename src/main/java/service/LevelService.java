package service;

import exception.StringLengthFormatException;
import exception.ValueFormatException;
import model.Level;
import validation.Validator;
import javax.servlet.http.HttpServletRequest;
import static java.lang.Integer.parseInt;


public class LevelService {

    Validator validator = new Validator();


    public boolean mainValidator(HttpServletRequest request){

        boolean validateInput = true;


        validateInput = validateInputName(request, validateInput, 3, 25 );
        validateInput = validateInputDescription(request, validateInput, 10, 100);
        validateInput = validateInputCoins(request, validateInput, 1, 10000);


        return validateInput;
    }

    public Level getInformationFromServlet(HttpServletRequest req){
        String name = req.getParameter("level-name");
        String description = req.getParameter("level-description");
        String coins = req.getParameter("level-coins");
        int price = parseInt(coins);

        Level level = new Level(name, description, price, "level6.svg");

        return level;
    }

    public Level getInformationToUpdate(HttpServletRequest request, Level level){
        String name = request.getParameter("level-name");
        String description = request.getParameter("level-description");
        String coins = request.getParameter("level-coins");
        int price = parseInt(coins);

        level.setName(name);
        level.setDescription(description);
        level.setPrice(price);

        return level;

    }

    public boolean validateInputName(HttpServletRequest request, boolean validateInput, int min,  int max){
        String levelName = request.getParameter("level-name");


        try{
            validator.validateStringLength(levelName, min, max);

        }catch(StringLengthFormatException e){
            request.setAttribute("name_validation_message", e.getMessage());
            validateInput = false;
        }
        return validateInput;
    }


    public boolean validateInputDescription(HttpServletRequest request, boolean validateInput, int min, int max){
        String levelName = request.getParameter("level-description");


        try{
            validator.validateStringLength(levelName, min, max);

        }catch(StringLengthFormatException e){
            request.setAttribute("description_validation_message", e.getMessage());
            validateInput = false;
        }
        return validateInput;
    }

    public boolean validateInputCoins(HttpServletRequest request, boolean isInputValid, int min, int max){
        try {
            validator.validateValue(request.getParameter("level-coins"), min, max);
        } catch (ValueFormatException e) {
            request.setAttribute("value_validation_message", e.getMessage());
            isInputValid = false;
        }
        return isInputValid;
    }



}
