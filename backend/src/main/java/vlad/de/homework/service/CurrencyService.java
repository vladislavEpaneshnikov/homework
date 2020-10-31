package vlad.de.homework.service;

import vlad.de.homework.domain.CustomRate;

import java.util.List;

public interface CurrencyService {

    void addCustomRate(CustomRate customRate);

    Double convertCurrency(String base, String currency, Double amount);

    void removeCustomCurrency(String base, String currency);

    List<CustomRate> getCustomRates();
}
