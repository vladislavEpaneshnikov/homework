package vlad.de.homework.service;

import vlad.de.homework.domain.CustomRate;

import java.util.List;

public interface CurrencyService {

    public void addCustomRate(CustomRate customRate);

    public Double convertCurrency(String base, String currency, Double amount);

    public void removeCustomCurrency(String base, String currency);

    public List<CustomRate> getCustomRates();
}
