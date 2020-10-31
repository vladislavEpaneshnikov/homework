package vlad.de.homework.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vlad.de.homework.exception.FailedToFetchRatesException;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Component
public class CurrencyRatesClient {

    @Value("${default.currency.rates.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public CurrencyRatesClient() {
        restTemplate = new RestTemplate();
    }

    /**
     * API returns data in format {"rates":{"USD":1.1819},"base":"EUR","date":"2020-10-26"} with base=EUR and symbols=USD
     */
    public CurrencyRates fetchDefaultRate(final String base, final String symbols) throws FailedToFetchRatesException {
        final String url = format("%s?base=%s&symbols=%s", baseUrl, base, symbols);
        try {
            return restTemplate.exchange(url, GET, null, CurrencyRates.class).getBody();
        } catch (final Exception e) {
            throw new FailedToFetchRatesException("Failed to call " + baseUrl);
        }
    }
}
