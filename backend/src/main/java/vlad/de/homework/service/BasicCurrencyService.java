package vlad.de.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.exception.FailedToFetchRatesException;
import vlad.de.homework.exception.NoRateFoundException;
import vlad.de.homework.integration.CurrencyRatesClient;
import vlad.de.homework.persistence.CustomRatePersistence;

import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

@Service
public class BasicCurrencyService implements CurrencyService {

    private final CurrencyRatesClient client;
    private final CustomRatePersistence persistence;
    private final Map<String, Double> defaultCurrencyRates;

    @Autowired
    public BasicCurrencyService(final CurrencyRatesClient client, final CustomRatePersistence persistence,
                                @Qualifier("currencyRates") final Map<String, Double> currencyRates) {
        this.client = client;
        this.persistence = persistence;
        this.defaultCurrencyRates = currencyRates;
    }

    @Override
    public void addCustomRate(final CustomRate customRate) {
        persistence.addCustomRate(customRate);
    }

    @Override
    public Double convertCurrency(final String base, final String currency, final Double amount) {
        final Double rate = getConversionRate(base, currency);
        return round(amount * rate * 100.0) / 100.0;
    }

    @Override
    public void removeCustomCurrency(final String base, final String currency) {
        persistence.removeCustomRate(base, currency);
    }

    @Override
    public List<CustomRate> getCustomRates() {
        return persistence.getCustomRatesList();
    }

    private Double getConversionRate(final String base, final String currency) {
        try {
            return persistence.getCustomRate(base, currency);
        } catch (final NoRateFoundException e) {
            return getDefaultConversionRate(base, currency);
        }
    }

    private Double getDefaultConversionRate(final String base, final String currency) {
        try {
            return client.fetchDefaultRate(base, currency).getRates().get(currency);
        } catch (final FailedToFetchRatesException ex) {
            return defaultCurrencyRates.get(currency) / defaultCurrencyRates.get(base);
        }
    }
}
