package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        return null;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        return List.of();
    }
}
