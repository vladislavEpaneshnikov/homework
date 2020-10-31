package vlad.de.homework.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.exception.NoRateFoundException;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomRatePersistence.class, CustomRatePersistenceTest.TestConfig.class})
class CustomRatePersistenceTest {

    private static final String BASE_CURRENCY = "USD";
    private static final String TARGET_CURRENCY = "EUR";
    private static final Double RATE = 0.1;
    private static final Double SAVED_RATE = 0.2;

    @Autowired
    private CustomRateRepository repository;

    @Autowired
    private CustomRatePersistence persistence;

    @Test
    void addCustomRate_newValue() {
        final CustomRate rate = new CustomRate(BASE_CURRENCY, TARGET_CURRENCY, RATE);

        when(repository.findAllByBaseCurrencyAndTargetCurrency(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(emptyList());

        persistence.addCustomRate(rate);

        verify(repository).save(rate);
        assertEquals(RATE, rate.getRate());
    }

    @Test
    void addCustomRate_updatingValue() {
        final CustomRate rate = new CustomRate(BASE_CURRENCY, TARGET_CURRENCY, RATE);
        final CustomRate savedRate = new CustomRate(BASE_CURRENCY, TARGET_CURRENCY, SAVED_RATE);

        when(repository.findAllByBaseCurrencyAndTargetCurrency(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(singletonList(savedRate));

        persistence.addCustomRate(rate);

        verify(repository).save(savedRate);
        assertEquals(RATE, savedRate.getRate());
    }

    @Test
    void removeCustomRate() {
        persistence.removeCustomRate(BASE_CURRENCY, TARGET_CURRENCY);
        verify(repository).deleteByBaseCurrencyAndTargetCurrency(BASE_CURRENCY, TARGET_CURRENCY);
    }

    @Test
    void getCustomRatesList() {
        final CustomRate rate = new CustomRate();

        when(repository.findAll()).thenReturn(singletonList(rate));

        final List<CustomRate> rates = persistence.getCustomRatesList();
        assertEquals(1, rates.size());
        assertEquals(rate, rates.get(0));
    }

    @Test
    void getCustomRate() throws NoRateFoundException {
        final CustomRate rate = new CustomRate(BASE_CURRENCY, TARGET_CURRENCY, RATE);

        when(repository.findAllByBaseCurrencyAndTargetCurrency(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(singletonList(rate));

        assertEquals(RATE, persistence.getCustomRate(BASE_CURRENCY, TARGET_CURRENCY));
    }

    @Test
    void getCustomRate_noRateFound() {
        when(repository.findAllByBaseCurrencyAndTargetCurrency(BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(emptyList());

        assertThrows(NoRateFoundException.class, () -> persistence.getCustomRate(BASE_CURRENCY, TARGET_CURRENCY));
    }

    static class TestConfig {

        @Bean
        public CustomRateRepository customRateRepository() {
            return mock(CustomRateRepository.class);
        }
    }
}