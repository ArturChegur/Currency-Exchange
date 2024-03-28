package dto;

import java.math.BigDecimal;

public class ResponseExchangeDto {
    private ResponseCurrencyDto baseCurrency;
    private ResponseCurrencyDto targetCurrency;
    private BigDecimal rate;
    private Double amount;
    private Double convertedAmount;

    public ResponseExchangeDto(){
    }

    public ResponseCurrencyDto getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(ResponseCurrencyDto baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public ResponseCurrencyDto getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(ResponseCurrencyDto targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}