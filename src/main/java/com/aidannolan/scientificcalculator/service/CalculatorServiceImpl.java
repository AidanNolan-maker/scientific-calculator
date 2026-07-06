package com.aidannolan.scientificcalculator.service;

import com.aidannolan.scientificcalculator.util.ExpressionEvaluator;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    private final ExpressionEvaluator evaluator;

    public CalculatorServiceImpl(ExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public String evaluate(String expression, String angleMode) {
        return evaluator.evaluate(expression, angleMode);
    }
}
