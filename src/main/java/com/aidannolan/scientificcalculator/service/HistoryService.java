package com.aidannolan.scientificcalculator.service;

import com.aidannolan.scientificcalculator.model.HistoryEntry;

import java.util.List;

public interface HistoryService {
    void addEntry(String expression, String result);
    List<HistoryEntry> getHistory();
    void clearHistory();
}
