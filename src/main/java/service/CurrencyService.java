package service;

import dao.CurrenciesDao;
import dto.RequestCurrencyDto;
import dto.ResponseCurrencyDto;
import entity.Currency;
import exceptions.DataExistsException;
import exceptions.DataNotFoundException;

import java.util.List;
import java.util.Optional;

import util.Mapper;

import static java.util.stream.Collectors.toList;

public class CurrencyService implements Service<ResponseCurrencyDto, RequestCurrencyDto> {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrenciesDao currenciesDao = CurrenciesDao.getInstance();

    private CurrencyService() {
    }

    @Override
    public List<ResponseCurrencyDto> findAll() {
        List<Currency> result = currenciesDao.findAll();
        if (result.isEmpty()) {
            throw new DataNotFoundException("There is no currencies right now");
        } else {
            return currenciesDao.findAll().stream()
                    .map(Mapper::currencyToResponseCurrencyDto)
                    .collect(toList());
        }
    }

    @Override
    public ResponseCurrencyDto findByCode(RequestCurrencyDto request) {
        Optional<Currency> currency = currenciesDao.findByCode(request);
        if (currency.isEmpty()) {
            throw new DataNotFoundException("Currency not found");
        } else {
            return Mapper.currencyToResponseCurrencyDto(currency.get());
        }
    }

    @Override
    public void add(RequestCurrencyDto request) {
        if (currenciesDao.findByCode(request).isPresent()) {
            throw new DataExistsException("This currency already exists");
        } else {
            currenciesDao.add(request);
        }
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}