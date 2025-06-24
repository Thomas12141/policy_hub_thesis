package org.example.billing_policy;

public interface PaymentBook {
    void initialize();
    void addPayment(PaymentKey key, double amount);
    double getPayment(PaymentKey key);
}
