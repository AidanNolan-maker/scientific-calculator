package com.aidannolan.scientificcalculator.service;

import com.aidannolan.scientificcalculator.model.HistoryEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private static final int MAX_HISTORY = 25;

    private final LinkedList<HistoryEntry> history = new LinkedList<>();

    @Override
    public synchronized void addEntry(String expression, String result) {
        history.addFirst(new HistoryEntry(expression, result, LocalDateTime.now()));

        if (history.size() > MAX_HISTORY)
            history.removeLast();
    }

    @Override
    public synchronized List<HistoryEntry> getHistory() {
        return List.copyOf(history);
    }

    @Override
    public synchronized void clearHistory() {
        history.clear();
    }
}
