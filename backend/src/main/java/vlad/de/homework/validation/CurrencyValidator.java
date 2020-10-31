package vlad.de.homework.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Autowired
    @Qualifier("currencyTypes")
    private List<String> currencyTypes;

    @Override
    public void initialize(ValidCurrency contactNumber) {
    }

    @Override
    public boolean isValid(final String currency, final ConstraintValidatorContext cxt) {
        return currencyTypes.contains(currency);
    }

}