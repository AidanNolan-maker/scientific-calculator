package com.aidannolan.scientificcalculator.util;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpressionEvaluator {
    private final ExpressionPreprocessor preprocessor;

    public String evaluate(String expression, String angleMode) {
        try {
            expression = preprocessor.preprocess(expression, angleMode);

            ExpressionConfiguration configuration = ExpressionConfiguration.defaultConfiguration();

            Expression expr = new Expression(expression, configuration);

            return expr.evaluate().getStringValue();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

}
