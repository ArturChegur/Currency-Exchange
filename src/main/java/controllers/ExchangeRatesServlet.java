package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestExchangeRateDto;
import dto.ResponseExchangeRateDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;
import util.ParameterValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ResponseExchangeRateDto> response = exchangeRateService.findAll();
        String jsonResponse = mapper.writeValueAsString(response);
        resp.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ParameterValidator.checkPath(req.getPathInfo());
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        ParameterValidator.checkCode(baseCurrencyCode);
        ParameterValidator.checkCode(targetCurrencyCode);
        ParameterValidator.checkRate(rate);
        RequestExchangeRateDto request = new RequestExchangeRateDto();
        request.setBaseCurrency(baseCurrencyCode);
        request.setTargetCurrency(targetCurrencyCode);
        request.setRate(new BigDecimal(rate));
        exchangeRateService.add(request);
    }
}