package vlad.de.homework.persistence;

import org.springframework.data.repository.CrudRepository;
import vlad.de.homework.domain.CustomRate;

import java.util.List;

public interface CustomRateRepository extends CrudRepository<CustomRate, Integer> {

    List<CustomRate> findAllByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);

    void deleteByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
}