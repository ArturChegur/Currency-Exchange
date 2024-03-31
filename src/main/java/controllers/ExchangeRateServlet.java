package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestExchangeRateDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;
import util.ParameterValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ParameterValidator.checkPath(req.getPathInfo());
        String path = req.getPathInfo().substring(1);
        ParameterValidator.checkCodePair(path);
        RequestExchangeRateDto request = new RequestExchangeRateDto();
        request.setBaseCurrency(path.substring(0, 3));
        request.setTargetCurrency(path.substring(3, 6));
        resp.getWriter().write(mapper.writeValueAsString(exchangeRateService.findByCode(request)));
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ParameterValidator.checkPath(req.getPathInfo());
        String path = req.getPathInfo().substring(1);
        ParameterValidator.checkCodePair(path);
        BufferedReader reader = req.getReader();
        String rate = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("rate=")) {
                int index = line.indexOf("rate=");
                rate = line.substring(index + 5);
            }
        }
        ParameterValidator.checkRate(rate);
        RequestExchangeRateDto request = new RequestExchangeRateDto();
        request.setBaseCurrency(path.substring(0, 3));
        request.setTargetCurrency(path.substring(3, 6));
        request.setRate(new BigDecimal(rate));
        ParameterValidator.checkCode(request.getTargetCurrency());
        ParameterValidator.checkCode(request.getBaseCurrency());
        exchangeRateService.update(request);
    }
}