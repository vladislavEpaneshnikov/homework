package vlad.de.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.exception.FailedToFetchRatesException;
import vlad.de.homework.exception.NoRateFoundException;
import vlad.de.homework.integration.CurrencyRates;
import vlad.de.homework.integration.CurrencyRatesClient;
import vlad.de.homework.persistence.CustomRatePersistence;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BasicCurrencyService.class, BasicCurrencyServiceTest.TestConfig.class})
class BasicCurrencyServiceTest {

    private static final String BASE_CURRENCY = "USD";
    private static final String TARGET_CURRENCY = "EUR";

    private static final Double DEFAULT_RATE_TARGET_CURRENCY = 2.0;
    private static final Double DEFAULT_RATE_BASE_CURRENCY = 0.5;
    private static final Double SAVED_RATE = 0.001;
    private static final Double FETCHED_RATE = 0.01;
    private static final Double RATE = 0.1;

    private static final Double AMOUNT = 100.0;
    private static final Double DEFAULT_CONVERTED_AMOUNT = AMOUNT * DEFAULT_RATE_TARGET_CURRENCY / DEFAULT_RATE_BASE_CURRENCY;

    @Autowired
    private BasicCurrencyService service;

    @Autowired
    private CurrencyRatesClient client;

    @Autowired
    private CustomRatePersistence persistence;

    @Test
    void addCustomRate() {
        final CustomRate rate = new CustomRate(BASE_CURRENCY, TARGET_CURRENCY, RATE);
        service.addCustomRate(rate);
        verify(persistence).addCustomRate(rate);
    }

    @Test
    void removeCustomCurrency() {
        service.removeCustomCurrency(BASE_CURRENCY, TARGET_CURRENCY);
        verify(persistence).removeCustomRate(BASE_CURRENCY, TARGET_CURRENCY);
    }

    @Test
    void getCustomRates() {
        service.getCustomRates();
        verify(persistence).getCustomRatesList();
    }

    @Test
    void convertCurrency() throws NoRateFoundException {
        when(persistence.getCustomRate(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(SAVED_RATE);
        assertEquals(AMOUNT * SAVED_RATE, service.convertCurrency(BASE_CURRENCY, TARGET_CURRENCY, AMOUNT));
    }

    @Test
    void convertCurrency_fetchingDefaultValues() throws NoRateFoundException, FailedToFetchRatesException {
        final Map<String, Double> rates = new HashMap<>();
        rates.put(TARGET_CURRENCY, FETCHED_RATE);
        final CurrencyRates currencyRates = new CurrencyRates(rates);

        doThrow(NoRateFoundException.class).when(persistence).getCustomRate(BASE_CURRENCY, TARGET_CURRENCY);
        when(client.fetchDefaultRate(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(currencyRates);

        assertEquals(AMOUNT * FETCHED_RATE, service.convertCurrency(BASE_CURRENCY, TARGET_CURRENCY, AMOUNT));
    }

    @Test
    void convertCurrency_fetchingDefaultProperties() throws NoRateFoundException, FailedToFetchRatesException {
        doThrow(NoRateFoundException.class).when(persistence).getCustomRate(BASE_CURRENCY, TARGET_CURRENCY);
        doThrow(FailedToFetchRatesException.class).when(client).fetchDefaultRate(BASE_CURRENCY, TARGET_CURRENCY);

        assertEquals(DEFAULT_CONVERTED_AMOUNT, service.convertCurrency(BASE_CURRENCY, TARGET_CURRENCY, AMOUNT));
    }

    static class TestConfig {

        @Bean
        public CurrencyRatesClient currencyRatesClient() {
            return mock(CurrencyRatesClient.class);
        }

        @Bean
        public CustomRatePersistence customRatePersistence() {
            return mock(CustomRatePersistence.class);
        }

        @Bean("currencyRates")
        public Map<String, Double> defaultCurrencyRates() {
            final Map<String, Double> rates = new HashMap<>();
            rates.put(BASE_CURRENCY, DEFAULT_RATE_BASE_CURRENCY);
            rates.put(TARGET_CURRENCY, DEFAULT_RATE_TARGET_CURRENCY);
            return rates;
        }
    }
}