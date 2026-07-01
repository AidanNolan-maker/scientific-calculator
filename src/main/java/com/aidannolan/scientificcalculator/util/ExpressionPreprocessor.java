package com.aidannolan.scientificcalculator.util;

import org.springframework.stereotype.Component;

@Component
public class ExpressionPreprocessor {
    public String preprocess(String expression) {
        if (expression == null || expression.isBlank())
            return expression;

        return expression.replace("ln(", "LOG(")
                         .replace("log(", "LOG10(")
                         .replace("√(", "SQRT(")
                         .replace("π", "PI")
                         .replace("×", "*")
                         .replace("÷", "/");
    }
}
