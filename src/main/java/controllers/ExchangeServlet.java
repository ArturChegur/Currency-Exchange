package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestExchangeDto;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;

import java.io.IOException;


@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        RequestExchangeDto request = new RequestExchangeDto(from, to, Double.valueOf(amount));
        resp.getWriter().write(mapper.writeValueAsString(exchangeRateService.exchange(request)));
    }
}