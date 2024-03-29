package dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ResponseExchangeDto {
    private ResponseCurrencyDto baseCurrency;
    private ResponseCurrencyDto targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public ResponseExchangeDto() {
    }

    public void setBaseCurrency(ResponseCurrencyDto baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(ResponseCurrencyDto targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public ResponseCurrencyDto getBaseCurrency() {
        return baseCurrency;
    }

    public ResponseCurrencyDto getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseExchangeDto that = (ResponseExchangeDto) o;
        return Objects.equals(baseCurrency, that.baseCurrency) && Objects.equals(targetCurrency, that.targetCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, targetCurrency);
    }
}