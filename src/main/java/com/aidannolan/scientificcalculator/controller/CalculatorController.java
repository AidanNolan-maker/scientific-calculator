package com.aidannolan.scientificcalculator.controller;

import com.aidannolan.scientificcalculator.model.CalculationRequest;
import com.aidannolan.scientificcalculator.model.CalculationResponse;
import com.aidannolan.scientificcalculator.model.HistoryEntry;
import com.aidannolan.scientificcalculator.service.CalculatorService;
import com.aidannolan.scientificcalculator.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final HistoryService historyService;

    public CalculatorController(CalculatorService calculatorService, HistoryService historyService) {
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public CalculationResponse calculate(@Valid @RequestBody CalculationRequest request) {
        try {
            String result = calculatorService.evaluate(request.getExpression());

            historyService.addEntry(request.getExpression(), result);

            return new CalculationResponse(true, result, null);
        } catch (Exception ex) {
            return new CalculationResponse(false, null, ex.getMessage());
        }
    }

    @GetMapping("/api/history")
    @ResponseBody
    public List<HistoryEntry> history() {
        return historyService.getHistory();
    }

    @DeleteMapping("/api/history")
    @ResponseBody
    public void clearHistory() {
        historyService.clearHistory();
    }
}
