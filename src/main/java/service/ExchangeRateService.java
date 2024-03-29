package service;

import dao.CurrenciesDao;
import dao.ExchangeRatesDao;
import dto.*;
import entity.Currency;
import entity.ExchangeRate;
import exceptions.DataExistsException;
import exceptions.DataNotFoundException;
import util.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ExchangeRateService implements Service<ResponseExchangeRateDto, RequestExchangeRateDto> {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private static final ExchangeRatesDao exchangeRateDao = ExchangeRatesDao.getInstance();
    private static final CurrenciesDao currenciesDao = CurrenciesDao.getInstance();

    private ExchangeRateService() {
    }

    @Override
    public List<ResponseExchangeRateDto> findAll() {
        List<ExchangeRate> result = exchangeRateDao.findAll();
        if (result.isEmpty()) {
            throw new DataNotFoundException("There is no exchange rates right now");
        } else {
            return result.stream()
                    .map(this::buildExchangeRateDto)
                    .collect(toList());
        }
    }

    @Override
    public ResponseExchangeRateDto findByCode(RequestExchangeRateDto request) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(request);
        if (exchangeRate.isEmpty()) {
            throw new DataNotFoundException("Exchange rate not found");
        } else {
            return buildExchangeRateDto(exchangeRate.get());
        }
    }

    @Override
    public void add(RequestExchangeRateDto request) {
        RequestCurrencyDto base = new RequestCurrencyDto();
        RequestCurrencyDto target = new RequestCurrencyDto();
        base.setCode(request.getBaseCurrency());
        target.setCode(request.getTargetCurrency());
        if (currenciesDao.findByCode(base).isEmpty() || currenciesDao.findByCode(target).isEmpty()) {
            throw new DataNotFoundException("Currency with this code was not found");
        } else if (exchangeRateDao.findByCode(request).isPresent()) {
            throw new DataExistsException("Exchange rate already exists");
        } else {
            exchangeRateDao.add(request);
        }
    }

    public void update(RequestExchangeRateDto request) {
        if (exchangeRateDao.findByCode(request).isEmpty()) {
            throw new DataNotFoundException("Exchange rate not found");
        } else {
            exchangeRateDao.update(request);
        }
    }

    public ResponseExchangeDto exchange(RequestExchangeDto request) {
        RequestExchangeRateDto requestDirect = Mapper.exchangeToRequestExchangeRateDto(request, true);
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(requestDirect);
        if (exchangeRate.isPresent()) {
            ResponseExchangeRateDto response = buildExchangeRateDto(exchangeRate.get());
            return Mapper.exchangeToResponseExchangeDto(response.getBaseCurrency(), response.getTargetCurrency(), response.getRate(), request.getAmount());
        }
        RequestExchangeRateDto requestReverse = Mapper.exchangeToRequestExchangeRateDto(request, false);
        exchangeRate = exchangeRateDao.findByCode(requestReverse);
        if (exchangeRate.isPresent()) {
            ResponseExchangeRateDto response = buildExchangeRateDto(exchangeRate.get());
            BigDecimal one = new BigDecimal("1");
            BigDecimal reverseRate = one.divide(response.getRate(), 5, RoundingMode.HALF_UP);
            return Mapper.exchangeToResponseExchangeDto(response.getTargetCurrency(), response.getBaseCurrency(), reverseRate, request.getAmount());
        }
        RequestExchangeRateDto baseUSD = Mapper.exchangeToRequestExchangeRateDto(request, false);
        RequestExchangeRateDto targetUSD = Mapper.exchangeToRequestExchangeRateDto(request, true);
        baseUSD.setBaseCurrency("USD");
        targetUSD.setBaseCurrency("USD");
        Optional<ExchangeRate> baseExchangeRate = exchangeRateDao.findByCode(baseUSD);
        Optional<ExchangeRate> targetExchangeRate = exchangeRateDao.findByCode(targetUSD);
        if (baseExchangeRate.isPresent() && targetExchangeRate.isPresent()) {
            ResponseExchangeRateDto baseResponseDto = buildExchangeRateDto(baseExchangeRate.get());
            ResponseExchangeRateDto targetResponseDto = buildExchangeRateDto(targetExchangeRate.get());
            BigDecimal rate = targetExchangeRate.get().getRate().divide(baseExchangeRate.get().getRate(), 5, RoundingMode.HALF_UP);
            return Mapper.exchangeToResponseExchangeDto(baseResponseDto.getTargetCurrency(), targetResponseDto.getTargetCurrency(), rate, request.getAmount());
        } else {
            throw new DataNotFoundException("Exchange can not be done");
        }
    }

    private ResponseExchangeRateDto buildExchangeRateDto(ExchangeRate exchangeRate) {
        Optional<Currency> baseCurrency = currenciesDao.findCurrencyById(exchangeRate.getBaseCurrencyId());
        Optional<Currency> targetCurrency = currenciesDao.findCurrencyById(exchangeRate.getTargetCurrencyId());
        if (baseCurrency.isEmpty() || targetCurrency.isEmpty()) {
            throw new DataNotFoundException("Exchange rate not found");
        } else {
            return new ResponseExchangeRateDto(exchangeRate.getId(),
                    Mapper.currencyToResponseCurrencyDto(baseCurrency.get()),
                    Mapper.currencyToResponseCurrencyDto(targetCurrency.get()),
                    exchangeRate.getRate());
        }
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}