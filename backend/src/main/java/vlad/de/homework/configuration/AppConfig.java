package vlad.de.homework.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Configuration
public class AppConfig {

    @Value("${currencies.rates.to.eur}")
    private String currencyRates;

    @Value("${currencies.types}")
    private String currencyTypes;

    @Bean("currencyRates")
    public Map<String, Double> currencyRates() {
        final Map<String, Double> defaultCurrencyRates = new HashMap<>();

        stream(currencyRates.split(","))
                .map(s -> s.split(":"))
                .forEach(entry -> defaultCurrencyRates.put(entry[0], parseDouble(entry[1])));

        return defaultCurrencyRates;
    }

    @Bean("currencyTypes")
    public List<String> currencyTypes() {
        return stream(currencyTypes.split(",")).sorted().collect(toList());
    }
}
