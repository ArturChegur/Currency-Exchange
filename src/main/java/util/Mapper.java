package util;

import dto.RequestExchangeDto;
import dto.RequestExchangeRateDto;
import dto.ResponseCurrencyDto;
import entity.Currency;

public final class Mapper {
    private Mapper() {
    }

    public static ResponseCurrencyDto currencyToResponseCurrencyDto(Currency currency) {
        return new ResponseCurrencyDto(currency.getId(),
                currency.getFullName(),
                currency.getCode(),
                currency.getSign());
    }

    public static RequestExchangeRateDto exchangeToRequestExchangeRateDto(RequestExchangeDto request, boolean isDirect){
        RequestExchangeRateDto result = new RequestExchangeRateDto();
        if (isDirect) {
            result.setBaseCurrency(request.getBaseCurrency());
            result.setTargetCurrency(request.getTargetCurrency());
        } else {
            result.setBaseCurrency(request.getTargetCurrency());
            result.setTargetCurrency(request.getBaseCurrency());
        }
        return result;
    }
}
