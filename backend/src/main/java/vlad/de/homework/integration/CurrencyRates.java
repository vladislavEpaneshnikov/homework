package vlad.de.homework.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CurrencyRates {

    private final Map<String, Double> rates;

    public CurrencyRates(@JsonProperty("rates") final Map<String, Double> rates) {
        this.rates = rates;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "CurrencyRates{" +
                "rates=" + rates +
                '}';
    }
}
