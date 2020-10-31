package vlad.de.homework.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:/application.properties")
@ContextConfiguration(classes = AppConfig.class)
class AppConfigTest {

    @Autowired
    private Map<String, Double> currencyRates;

    @Autowired
    private List<String> currencyTypes;

    @Test
    void currencyRates() {
        assertEquals(1.00, currencyRates.get("EUR"));
        assertEquals(1.5589, currencyRates.get("CAD"));
        assertEquals(9.1699, currencyRates.get("HKD"));
    }

    @Test
    void currencyTypes() {
        assertEquals("CAD", currencyTypes.get(0));
        assertEquals("EUR", currencyTypes.get(1));
        assertEquals("HKD", currencyTypes.get(2));
    }
}