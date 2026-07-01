package com.aidannolan.scientificcalculator.model;

import jakarta.validation.constraints.NotBlank;

public class CalculationRequest {
    @NotBlank(message = "Expression cannot be empty.")
    private String expression;

    public CalculationRequest() {}

    public CalculationRequest(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
