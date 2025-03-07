package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();
    private Map<String, Order> orderData = new HashMap<>();

    public Payment save(Payment payment, Order order) {
        orderData.put(payment.getId(), order);
        return this.save(payment);
    }

    public Payment save(Payment payment) {
        if (!orderData.containsKey(payment.getId())) {
            throw new IllegalArgumentException();
        }

        int i = 0;

        for (Payment saved : paymentData) {
            if (saved.getId().equals(payment.getId())) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }

            i += 1;
        }

        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment payment : paymentData) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }

        return null;
    }

    public Order findOrderByPaymentId(String paymentId) {
        return orderData.get(paymentId);
    }

    public List<Payment> findAll() {
        return paymentData;
    }
}
