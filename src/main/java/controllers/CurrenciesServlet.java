package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestCurrencyDto;
import dto.ResponseCurrencyDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import util.ParameterValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ResponseCurrencyDto> result = currencyService.findAll();
        String jsonResponse = mapper.writeValueAsString(result);
        resp.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        ParameterValidator.checkCode(code);
        ParameterValidator.checkName(name);
        ParameterValidator.checkSign(sign);
        currencyService.add(new RequestCurrencyDto(name, code, sign));
        resp.setStatus(201);
    }
}