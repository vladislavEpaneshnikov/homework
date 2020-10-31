package vlad.de.homework.rest;

import vlad.de.homework.validation.ValidCurrency;

public class Currency {

    @ValidCurrency
    private String baseCurrency;
    @ValidCurrency
    private String targetCurrency;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
