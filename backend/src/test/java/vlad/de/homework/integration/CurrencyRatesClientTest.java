package vlad.de.homework.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import vlad.de.homework.exception.FailedToFetchRatesException;

import java.util.HashMap;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:/application.properties")
@ContextConfiguration(classes = CurrencyRatesClient.class)
class CurrencyRatesClientTest {

    private static final String BASE = "USD";
    private static final String SYMBOLS = "EUR";

    private RestTemplate restTemplate;

    @Value("${default.currency.rates.url}")
    private String baseUrl;

    @Autowired
    private CurrencyRatesClient currencyRatesClient;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        setField(currencyRatesClient, "restTemplate", restTemplate);
    }

    @Test
    void fetchDefaultRate() throws FailedToFetchRatesException {
        final CurrencyRates currencyRates = new CurrencyRates(new HashMap<>());
        final ResponseEntity<CurrencyRates> entity = mock(ResponseEntity.class);

        when(entity.getBody()).thenReturn(currencyRates);
        when(restTemplate.exchange(getRequestUrl(), GET, null, CurrencyRates.class)).thenReturn(entity);

        assertEquals(currencyRates, currencyRatesClient.fetchDefaultRate(BASE, SYMBOLS));
    }

    @Test
    void fetchDefaultRate_throwsError() {

        when(restTemplate.exchange(getRequestUrl(), GET, null, CurrencyRates.class)).thenThrow(RuntimeException.class);

        assertThrows(FailedToFetchRatesException.class, () -> currencyRatesClient.fetchDefaultRate(BASE, SYMBOLS));
    }

    private String getRequestUrl() {
        return format("%s?base=%s&symbols=%s", baseUrl, BASE, SYMBOLS);
    }
}