package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        Map<String, String> paymentData = new HashMap<>();

        paymentData.clear();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payments.add(new Payment("56530a8a-f336-46d5-977b-69b29a32e489", "VOUCHER_CODE", paymentData));

        paymentData.clear();
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "100000");
        payments.add(new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData));
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.getFirst();
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertSame(payments.get(1).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("asdf");
        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> findAllResult = paymentRepository.findAll();
        assertEquals(payments.size(), findAllResult.size());
    }
}
