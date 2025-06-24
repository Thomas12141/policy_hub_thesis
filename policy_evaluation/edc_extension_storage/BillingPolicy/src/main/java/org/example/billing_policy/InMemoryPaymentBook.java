package org.example.billing_policy;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPaymentBook implements PaymentBook {
    private final Map<PaymentKey, Double> payments = new HashMap<>();

    private static InMemoryPaymentBook instance;

    private InMemoryPaymentBook() {
    }

    public static InMemoryPaymentBook getInstance() {
        if (instance == null) {
            instance = new InMemoryPaymentBook();
        }
        return instance;
    }

    @Override
    public void initialize() {
        //No initialization needed
    }

    @Override
    public void addPayment(PaymentKey key, double amount) {
        if (payments.containsKey(key)) {
            amount += payments.get(key);
        }
        payments.put(key, amount);

    }

    @Override
    public double getPayment(PaymentKey key) {
        return payments.getOrDefault(key, 0.0);
    }

    public void clear() {
        payments.clear();
    }
}
