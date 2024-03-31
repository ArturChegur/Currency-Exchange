package util;

import exceptions.DataNotFoundException;
import exceptions.InvalidDataException;

import java.math.BigDecimal;

public final class ParameterValidator {
    private ParameterValidator() {
    }

    public static void checkCode(String code) {
        if (code == null || code.length() != 3) {
            throw new InvalidDataException("Currency code is incorrect");
        }
    }

    public static void checkSign(String sign) {
        if (sign == null || sign.length() != 1) {
            throw new InvalidDataException("Sign is incorrect");
        }
    }

    public static void checkName(String name) {
        if (name == null || name.length() > 30) {
            throw new InvalidDataException("Name is incorrect");
        }
    }

    public static void checkCodePair(String path) {
        if (path == null || path.length() != 6) {
            throw new InvalidDataException("Currency codes are incorrect");
        }
    }

    public static void checkRate(String rate) {
        if (rate == null) {
            throw new InvalidDataException("Rate field is required");
        }
        try {
            BigDecimal check = new BigDecimal(rate);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Rate is incorrect");
        }
    }

    public static void checkPath(String pathInfo) {
        if (pathInfo == null) {
            throw new DataNotFoundException("Path is empty");
        }
    }
}