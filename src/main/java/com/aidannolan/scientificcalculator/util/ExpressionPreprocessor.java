package com.aidannolan.scientificcalculator.util;

import org.springframework.stereotype.Component;

@Component
public class ExpressionPreprocessor {
    public String preprocess(String expression, String angleMode) {
        if (expression == null || expression.isBlank())
            return expression;

        if ("RAD".equals(angleMode)) {
            expression = convertRadiansToDegrees(expression, "sin");
            expression = convertRadiansToDegrees(expression, "cos");
            expression = convertRadiansToDegrees(expression, "tan");
        }

        expression = convertFactorials(expression);

        expression = normalizeSymbols(expression);

        return expression;
    }

    private String convertRadiansToDegrees(String expression, String functionName) {
        StringBuilder builder = new StringBuilder(expression);

        int searchIndex = 0;

        while (true) {
            int functionIndex = builder.indexOf(functionName + "(", searchIndex);

            if (functionIndex == -1)
                break;

            int argumentStart = functionIndex + functionName.length() + 1;

            int depth = 1;
            int argumentEnd = argumentStart;

            while (argumentEnd < builder.length() && depth > 0) {
                char c = builder.charAt(argumentEnd);

                if (c == '(')
                    depth++;
                else if (c == ')')
                    depth--;

                argumentEnd++;
            }

            if (depth != 0)
                throw new IllegalArgumentException("Mismatched parentheses in expression.");

            String argument = builder.substring(argumentStart, argumentEnd - 1);

            String replacement = functionName + "((" + argument + ") * 180 / PI)";

            builder.replace(functionIndex, argumentEnd, replacement);

            searchIndex = functionIndex + replacement.length();
        }

        return builder.toString();
    }

    private String normalizeSymbols(String expression) {
        return expression.replace("ln(", "LOG(")
                .replace("log(", "LOG10(")
                .replace("√(", "SQRT(")
                .replace("π", "PI")
                .replace("×", "*")
                .replace("÷", "/");
    }

    private String convertFactorials(String expression) {
        return expression;
    }
}
