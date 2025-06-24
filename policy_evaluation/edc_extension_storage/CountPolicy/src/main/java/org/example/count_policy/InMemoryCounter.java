package org.example.count_policy;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCounter implements TransferCounter {
    private final Map<CounterKey, Integer> counter = new HashMap<>();

    private static InMemoryCounter instance;

    private InMemoryCounter() {
    }

    public static InMemoryCounter getInstance() {
        if (instance == null) {
            instance = new InMemoryCounter();
        }
        return instance;
    }


    @Override
    public void initialize() {
        //No initialization needed
    }

    @Override
    public void increment(CounterKey key) {
        counter.put(key, counter.getOrDefault(key, 0) + 1);
    }

    @Override
    public int get(CounterKey key) {
        return counter.getOrDefault(key, 0);
    }
}
