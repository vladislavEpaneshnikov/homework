package vlad.de.homework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CustomRate {

    @Id
    @GeneratedValue
    @Column
    @JsonIgnore
    private Integer id;
    @Column
    private String baseCurrency;
    @Column
    private String targetCurrency;
    @Column
    private Double rate;

    public CustomRate() {
    }

    public CustomRate(final String baseCurrency, final String targetCurrency, final Double rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String base) {
        this.baseCurrency = base;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String currency) {
        this.targetCurrency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CustomRate{" +
                "id=" + id +
                ", base='" + baseCurrency + '\'' +
                ", currency='" + targetCurrency + '\'' +
                ", rate=" + rate +
                '}';
    }
}