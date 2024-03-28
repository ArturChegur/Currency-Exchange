package dto;

public class RequestExchangeDto {
    private String baseCurrency;
    private String targetCurrency;
    private Double amount;

    public RequestExchangeDto(String baseCurrency, String targetCurrency, Double amount) {
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public Double getAmount() {
        return amount;
    }
}