package org.example.count_policy;

public interface TransferCounter {
    void initialize();
    void increment(CounterKey key);
    int get(CounterKey key);
}
