package vlad.de.homework.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import vlad.de.homework.domain.CustomRate;
import vlad.de.homework.service.BasicCurrencyService;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MainControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<String> CURRENCY_TYPES = asList("EUR", "USD");
    private static final String BASE_CURRENCY = "EUR";
    private static final String TARGET_CURRENCY = "USD";
    private static final String UNSUPPORTED_CURRENCY = "LAT";
    private static final Double MIN_RATE = 0.1;
    private static final Double MAX_RATE = 999999999.99;
    private static final Double MIN_AMOUNT = 0.1;
    private static final Double MAX_AMOUNT = 999999999.99;

    private static final String CURRENCY_LIST_URL = "/currencies";
    private static final String CUSTOM_RATES_LIST_URL = "/list";
    private static final String REMOVE_CUSTOM_RATE_URL = "/currency?baseCurrency=%s&targetCurrency=%s";
    private static final String ADD_CUSTOM_RATE_URL = "/currency?baseCurrency=%s&targetCurrency=%s&rate=%s";
    private static final String CONVERT_URL = "/currency?baseCurrency=%s&targetCurrency=%s&amount=%s";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasicCurrencyService service;

    @Captor
    private ArgumentCaptor<CustomRate> customRateCaptor;

    @Test
    void getCurrencies() throws Exception {
        assertEquals(MAPPER.writeValueAsString(CURRENCY_TYPES),
                mockMvc.perform(get(CURRENCY_LIST_URL))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    @Test
    void getCustomRates() throws Exception {
        final List<CustomRate> customRates = asList(new CustomRate(), new CustomRate());

        when(service.getCustomRates()).thenReturn(customRates);

        assertEquals(MAPPER.writeValueAsString(customRates),
                mockMvc.perform(get(CUSTOM_RATES_LIST_URL))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    @Test
    void removeCustomRate() throws Exception {
        mockMvc.perform(delete(format(REMOVE_CUSTOM_RATE_URL, BASE_CURRENCY, TARGET_CURRENCY)))
                .andExpect(status().isOk());

        verify(service).removeCustomCurrency(BASE_CURRENCY, TARGET_CURRENCY);
    }

    @Test
    void removeCustomRate_wrongBaseCurrency() throws Exception {
        mockMvc.perform(delete(format(REMOVE_CUSTOM_RATE_URL, UNSUPPORTED_CURRENCY, TARGET_CURRENCY)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeCustomRate_wrongTargetCurrency() throws Exception {
        mockMvc.perform(delete(format(REMOVE_CUSTOM_RATE_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomRate() throws Exception {
        mockMvc.perform(post(format(ADD_CUSTOM_RATE_URL, BASE_CURRENCY, TARGET_CURRENCY, MIN_RATE)))
                .andExpect(status().isOk());

        verify(service).addCustomRate(customRateCaptor.capture());
        final CustomRate rate = customRateCaptor.getValue();
        assertEquals(BASE_CURRENCY, rate.getBaseCurrency());
        assertEquals(TARGET_CURRENCY, rate.getTargetCurrency());
        assertEquals(MIN_RATE, rate.getRate());
    }

    @Test
    void addCustomRate_wrongBaseCurrency() throws Exception {
        mockMvc.perform(post(format(ADD_CUSTOM_RATE_URL, UNSUPPORTED_CURRENCY, TARGET_CURRENCY, MIN_RATE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomRate_wrongTargetCurrency() throws Exception {
        mockMvc.perform(post(format(ADD_CUSTOM_RATE_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MIN_RATE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomRate_wrongRate() throws Exception {
        mockMvc.perform(post(format(ADD_CUSTOM_RATE_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MIN_RATE - 0.1)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(format(ADD_CUSTOM_RATE_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MAX_RATE + 0.1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void convertCurrency() throws Exception {
        final Double convertedAmount = 123.12;

        when(service.convertCurrency(BASE_CURRENCY, TARGET_CURRENCY, MIN_AMOUNT)).thenReturn(convertedAmount);

        assertEquals(MAPPER.writeValueAsString(convertedAmount),
                mockMvc.perform(get(format(CONVERT_URL, BASE_CURRENCY, TARGET_CURRENCY, MIN_AMOUNT)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    @Test
    void convertCurrency_wrongBaseCurrency() throws Exception {
        mockMvc.perform(get(format(CONVERT_URL, UNSUPPORTED_CURRENCY, TARGET_CURRENCY, MIN_AMOUNT)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void convertCurrency_wrongTargetCurrency() throws Exception {
        mockMvc.perform(get(format(CONVERT_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MIN_AMOUNT)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void convertCurrency_wrongAmount() throws Exception {
        mockMvc.perform(get(format(CONVERT_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MIN_AMOUNT - 0.1)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(format(CONVERT_URL, BASE_CURRENCY, UNSUPPORTED_CURRENCY, MAX_AMOUNT + 0.1)))
                .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    static class AdditionalConfig {
        @Bean("currencyTypes")
        public List<String> getSomeBean() {
            return CURRENCY_TYPES;
        }
    }
}