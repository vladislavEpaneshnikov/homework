package vlad.de.homework.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.exception.NoRateFoundException;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Transactional
public class CustomRatePersistence {

    private final CustomRateRepository repository;

    @Autowired
    public CustomRatePersistence(final CustomRateRepository repository) {
        this.repository = repository;
    }

    public void addCustomRate(final CustomRate customRate) {
        final List<CustomRate> storedRates = repository.findAllByBaseCurrencyAndTargetCurrency(
                customRate.getBaseCurrency(), customRate.getTargetCurrency());

        if (isEmpty(storedRates)) {
            repository.save(customRate);
        } else {
            final CustomRate rate = storedRates.get(0);
            rate.setRate(customRate.getRate());
            repository.save(rate);
        }
    }

    public void removeCustomRate(final String base, final String currency) {
        repository.deleteByBaseCurrencyAndTargetCurrency(base, currency);
    }

    public Double getCustomRate(final String base, final String currency) throws NoRateFoundException {
        final List<CustomRate> storedRates = repository.findAllByBaseCurrencyAndTargetCurrency(base, currency);
        if (isEmpty(storedRates)) {
            throw new NoRateFoundException("This rate is not set");
        }
        return storedRates.get(0).getRate();
    }

    public List<CustomRate> getCustomRatesList() {
        return stream(repository.findAll().spliterator(), false).collect(toList());
    }
}
