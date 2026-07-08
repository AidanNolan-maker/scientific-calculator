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
        StringBuilder builder = new StringBuilder(expression);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) != '!')
                continue;

            if (i > 0 && builder.charAt(i - 1) == ')') {
                int end = i - 1;

                int depth = 1;
                int start = end - 1;

                while (start >= 0 && depth > 0) {
                    char c = builder.charAt(start);

                    if (c == ')')
                        depth++;
                    else if (c == '(')
                        depth--;

                    start--;
                }

                if (depth != 0)
                    throw new IllegalArgumentException("Mismatched parentheses.");

                start++;

                String argument = builder.substring(start, end + 1);

                String replacement = "FACT(" + argument + ")";

                builder.replace(start, i + 1, replacement);

                i = start + replacement.length();

                continue;
            }

            if (i > 0 && Character.isDigit(builder.charAt(i - 1))) {
                int start = i - 1;

                while (start > 0 && Character.isDigit(builder.charAt(start - 1)))
                    start--;

                String argument = builder.substring(start, i);

                String replacement = "FACT(" + argument + ")";

                builder.replace(start, i + 1, replacement);

                i = start + replacement.length() - 1;

                continue;
            }

            throw new IllegalArgumentException("Factorial must follow a number or a parenthesized expression.");
        }

        return builder.toString();
    }
}
