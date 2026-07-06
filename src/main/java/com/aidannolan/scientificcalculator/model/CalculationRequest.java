package com.aidannolan.scientificcalculator.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    @NotBlank(message = "Expression cannot be empty.")
    private String expression;

    private String angleMode;
}
