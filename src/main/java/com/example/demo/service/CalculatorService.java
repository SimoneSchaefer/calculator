package com.example.demo.service;

import com.example.demo.exception.InvalidFormulaException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CalculatorService {
    private final List<String> allowedOperators = Arrays.asList("+", "-", "/", "*");

    public boolean valid(String formula) {
        String formatted = prepare(formula);
        String aaa = formatted.replace(".", ""); // FIXME
        Pattern pattern = Pattern.compile("^\\d+([+|\\-|\\/|*]\\d+)+$");
        Matcher matcher = pattern.matcher(aaa);
        return matcher.matches();
    }

    public Double calculate(final String formula) {
        if (!valid(formula)) {
            throw new InvalidFormulaException("invalid formula");
        }
        String formatted = prepare(formula);
        Double currentResult = null;
        String lastParsedNumber = "";
        String lastOperator = null;
        StringBuilder currentParsedNumber = new StringBuilder();
        for (int counter = 0; counter < formatted.length(); counter ++) {
            Character currentChar = formatted.charAt(counter);
            if (isNumeric(currentChar)) {
                currentParsedNumber.append(currentChar);
            } else if (currentChar.equals('.')) {
                currentParsedNumber.append(".");
            } else if (isOperator(currentChar)) {
                currentResult = calculateLastBatch(currentResult, currentParsedNumber.toString(), lastOperator);
                lastOperator = currentChar.toString();
                currentParsedNumber = new StringBuilder();
            } else {
                throw new InvalidFormulaException(String.format(
                        "something went wrong while parsing %s, formula is %s, lastParsedNumber is %s", currentChar, formatted, lastParsedNumber)
                );
            }
        }
        currentResult = calculateLastBatch(currentResult, currentParsedNumber.toString(), lastOperator);
        return currentResult;
    }

    private boolean isOperator(Character input) {
        if (input == null) {
            return false;
        }
        return this.allowedOperators.contains(input.toString());
    }

    private boolean isNumeric(Character input) {
        if (input == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(input.toString());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private String prepare(String formula) {
        return formula.replace(" ", "");
    }

    private Double calculateLastBatch(Double result, String currentParsedNumber, String lastOperator) {
        Double currentNumber = Double.parseDouble(currentParsedNumber.toString());
        if (result == null) {
            return currentNumber;
        }
        if (lastOperator.equals("+")) {
            return result + currentNumber;
        }
        if (lastOperator.equals("-")) {
            return result - currentNumber;
        }
        if (lastOperator.equals("/")) {
            return result / currentNumber;
        }
        if (lastOperator.equals("*")) {
            return result * currentNumber;
        }
        throw new InvalidFormulaException(String.format("Invalid Operator %s", lastOperator));
    }
}
