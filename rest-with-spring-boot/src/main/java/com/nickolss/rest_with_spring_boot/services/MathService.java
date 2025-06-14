package com.nickolss.rest_with_spring_boot.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.nickolss.rest_with_spring_boot.exception.UnsupportedMathOperationException;
import com.nickolss.rest_with_spring_boot.util.NumberConverter;

@Service
public class MathService {
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }

        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo);
    }

    public Double subtraction(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo);
    }

    public Double multiplication(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo);
    }

    public Double division(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        if (NumberConverter.convertToDouble(numberTwo) == 0) {
            throw new UnsupportedMathOperationException("Cannot divide by zero");
        }
        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo);
    }

    public Double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return (NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)) / 2;
    }

    public Double squareRoot(@PathVariable("number") String number) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        Double num = NumberConverter.convertToDouble(number);
        if (num < 0) {
            throw new UnsupportedMathOperationException("Cannot calculate square root of a negative number");
        }
        return Math.sqrt(num);
    }
}
