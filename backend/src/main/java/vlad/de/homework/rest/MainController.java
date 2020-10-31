package vlad.de.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.service.BasicCurrencyService;
import vlad.de.homework.service.CurrencyService;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    private final List<String> currencyTypes;
    private final CurrencyService service;

    @Autowired
    public MainController(final BasicCurrencyService service, @Qualifier("currencyTypes") final List<String> currencyTypes) {
        this.service = service;
        this.currencyTypes = currencyTypes;
    }

    @RequestMapping(value = "currencies", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCurrencies() {
        return ok(currencyTypes);
    }

    @RequestMapping(value = "list", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomRate>> getCustomRates() {
        return ok(service.getCustomRates());
    }

    @RequestMapping(value = "currency", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCustomRate(@Valid final Currency currency) {

        service.removeCustomCurrency(currency.getBaseCurrency(), currency.getTargetCurrency());

        return ok("Done!");
    }

    @RequestMapping(value = "currency", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCustomRate(@Valid final Currency currency,
                                                @RequestParam @DecimalMin("0.01") @DecimalMax("999999999.99") final double rate) {

        service.addCustomRate(new CustomRate(currency.getBaseCurrency(), currency.getTargetCurrency(), rate));

        return ok("Done!");
    }

    @RequestMapping(value = "currency", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> convertCurrency(@Valid final Currency currency,
                                                  @RequestParam @DecimalMin("0.01") @DecimalMax("999999999.99") final double amount) {

        return ok(service.convertCurrency(currency.getBaseCurrency(), currency.getTargetCurrency(), amount));
    }
}
