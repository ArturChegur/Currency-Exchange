package controllers;

import dto.CurrencyDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.ls.LSOutput;
import service.CurrencyService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //todo коды ошибок
        // 200 если все в порядке 400 - нет валюты в адресе 404 - валюта не найдена 500 - ошибка на стороне сервера
        resp.setContentType("application/json");
        String[] uriSegments = req.getRequestURI().split("/");
        System.out.println(Arrays.toString(uriSegments));
        try (var printWriter = resp.getWriter()) {
            if (uriSegments.length > 2) {
                String currencyCode = uriSegments[uriSegments.length - 1];
                Optional<CurrencyDto> currencyDto = currencyService.findCurrencyByCode(currencyCode);
                if (currencyDto.isPresent()) {
                    printWriter.write(currencyDto.get().toString());
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency code");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency endpoint");
            }
        }
    }
}
