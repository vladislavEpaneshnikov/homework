package vlad.de.homework.persistence;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import vlad.de.homework.domain.CustomRate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
class CustomRateRepositoryTest {

    private static final String CURRENCY1 = "EUR";
    private static final String CURRENCY2 = "USD";
    private static final Double RATE1 = 0.1;
    private static final Double RATE2 = 0.99;

    @Autowired
    private CustomRateRepository repository;

    @Test
    void findAllByBaseCurrencyAndTargetCurrency() {
        repository.save(new CustomRate(CURRENCY1, CURRENCY2, RATE1));
        repository.save(new CustomRate(CURRENCY2, CURRENCY1, RATE2));

        assertEquals(RATE1, repository.findAllByBaseCurrencyAndTargetCurrency(CURRENCY1, CURRENCY2).get(0).getRate());
        assertEquals(RATE2, repository.findAllByBaseCurrencyAndTargetCurrency(CURRENCY2, CURRENCY1).get(0).getRate());
    }

    @Test
    void deleteByBaseCurrencyAndTargetCurrency() {
        repository.save(new CustomRate(CURRENCY1, CURRENCY2, RATE2));
        assertEquals(1, repository.findAllByBaseCurrencyAndTargetCurrency(CURRENCY1, CURRENCY2).size());

        repository.deleteByBaseCurrencyAndTargetCurrency(CURRENCY1, CURRENCY2);
        assertEquals(0, repository.findAllByBaseCurrencyAndTargetCurrency(CURRENCY1, CURRENCY2).size());
    }
}