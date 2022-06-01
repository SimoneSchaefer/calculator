package com.example.demo.controller;

import com.example.demo.model.Formula;
import com.example.demo.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class CalculatorController {
    @Autowired
    CalculatorService calculatorService;

    @PutMapping("/calculate")
    public Double calculate(@RequestBody Formula formula) {
        return calculatorService.calculate(formula.getFormula());
    }
}
