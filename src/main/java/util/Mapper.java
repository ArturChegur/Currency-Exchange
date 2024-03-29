package util;

import dto.*;
import entity.Currency;

import java.math.BigDecimal;

public final class Mapper {
    private Mapper() {
    }

    public static ResponseCurrencyDto currencyToResponseCurrencyDto(Currency currency) {
        return new ResponseCurrencyDto(currency.getId(),
                currency.getFullName(),
                currency.getCode(),
                currency.getSign());
    }

    public static RequestExchangeRateDto exchangeToRequestExchangeRateDto(RequestExchangeDto request, boolean isDirect) {
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


    public static ResponseExchangeDto exchangeToResponseExchangeDto(ResponseCurrencyDto base, ResponseCurrencyDto target, BigDecimal rate, BigDecimal amount) {
        ResponseExchangeDto result = new ResponseExchangeDto();
        result.setBaseCurrency(base);
        result.setTargetCurrency(target);
        result.setRate(rate);
        result.setAmount(amount);
        result.setConvertedAmount(rate.multiply(amount));
        return result;
    }
}