package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestExchangeDto;

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


@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        ParameterValidator.checkCode(from);
        ParameterValidator.checkCode(to);
        ParameterValidator.checkRate(amount);
        RequestExchangeDto request = new RequestExchangeDto(from, to, new BigDecimal(amount));
        resp.getWriter().write(mapper.writeValueAsString(exchangeRateService.exchange(request)));
    }
}