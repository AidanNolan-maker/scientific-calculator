package com.aidannolan.scientificcalculator.util;

import com.ezylang.evalex.Expression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpressionEvaluator {
    private final ExpressionPreprocessor preprocessor;

    public String evaluate(String expression, String angleMode) {
        try {
            expression = preprocessor.preprocess(expression, angleMode);

            System.out.println("Processed expression: " + expression);

            Expression expr = new Expression(expression);

            return expr.evaluate().getStringValue();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

}
