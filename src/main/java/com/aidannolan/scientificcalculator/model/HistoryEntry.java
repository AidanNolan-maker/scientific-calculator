package com.aidannolan.scientificcalculator.model;

import java.time.LocalDateTime;

public class HistoryEntry {
    private String expression;
    private String result;
    private LocalDateTime timestamp;

    public HistoryEntry() {}

    public HistoryEntry(String expression, String result, LocalDateTime timestamp) {
        this.expression = expression;
        this.result = result;
        this.timestamp = timestamp;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
