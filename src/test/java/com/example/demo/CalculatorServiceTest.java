package com.example.demo;


import com.example.demo.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class CalculatorServiceTest {
    CalculatorService calculatorService = new CalculatorService();

    @Test
    public void should_calculate_valid() throws Exception {
        Assertions.assertEquals(calculatorService.calculate("1 + 2"), 3);
        Assertions.assertEquals(calculatorService.calculate("1 + 2 + 20"), 23);
        Assertions.assertEquals(calculatorService.calculate("8 * 3"), 24);
        Assertions.assertEquals(calculatorService.calculate("18 / 3"), 6);
        Assertions.assertEquals(calculatorService.calculate("18 -3 / 5  * 12"), 36);
        Assertions.assertEquals(calculatorService.calculate("1.2 + 3.4"), 4.6);
        Assertions.assertEquals(calculatorService.calculate("-1.2 + 3.4"), 2.2);
    }


    @Test
    public void valid_should_reject_invalid_formulas() {
        List<String> invalidFormulas = Arrays.asList("", "abc", "abc + def", "123", "123 + 456 -");
        for (String example : invalidFormulas) {
            Assertions.assertFalse(calculatorService.valid(example), example);
        }
    }

    @Test
    public void valid_should_allow_valid_formulas() {
        List<String> invalidFormulas = Arrays.asList("1 + 2", "1 + 2 + 3", "1    +4", "1/3", "12345*345", "12.5 + 14.2 / 0.3");
        for (String example : invalidFormulas) {
            Assertions.assertTrue(calculatorService.valid(example), example);
        }
    }
}
