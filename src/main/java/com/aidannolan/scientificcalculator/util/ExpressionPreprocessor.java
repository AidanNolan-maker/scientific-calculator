package com.aidannolan.scientificcalculator.util;

import org.springframework.stereotype.Component;

@Component
public class ExpressionPreprocessor {
    public String preprocess(String expression, String angleMode) {
        if (expression == null || expression.isBlank() || !"DEG".equals(angleMode))
            return expression;

        expression = convertDegreesToRadians(expression, "sin");

        return expression.replace("ln(", "LOG(")
                         .replace("log(", "LOG10(")
                         .replace("√(", "SQRT(")
                         .replace("π", "PI")
                         .replace("×", "*")
                         .replace("÷", "/");
    }

    private String convertDegreesToRadians(String expression, String functionName) {
        return expression;
    }
}
