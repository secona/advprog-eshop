package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
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
    List<Order> orders;

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

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("3e8c353b-ff26-4dae-b925-fb7bd7c807f6");
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        products.add(product1);

        orders = new ArrayList<>();

        orders.add(new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", products, 1708560000L, "Guts"));
        orders.add(new Order("f55e5029-9f30-43e7-87b0-7e979f392c24", products, 1708560000L, "Guts"));
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();
        paymentRepository.save(payment, order);

        Payment paymentResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), paymentResult.getId());
        assertEquals(payment.getStatus(), paymentResult.getStatus());
        assertEquals(payment.getMethod(), paymentResult.getMethod());
        assertSame(payment.getPaymentData(), paymentResult.getPaymentData());

        Order orderResult = paymentRepository.findOrderByPaymentId(payment.getId());
        assertEquals(order.getId(), orderResult.getId());

        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testSaveCreateFailsIfNoOrder() {
        Payment payment = payments.getFirst();

        assertThrows(IllegalArgumentException.class, () -> paymentRepository.save(payment));
    }

    @Test
    void testSaveUpdatePayment() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();
        paymentRepository.save(payment, order);

        Payment newPayment = new Payment(payment.getId(), payments.get(1).getMethod(), payments.get(1).getPaymentData());
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(newPayment.getMethod(), findResult.getMethod());
        assertSame(newPayment.getPaymentData(), findResult.getPaymentData());

        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (int i = 0; i < payments.size(); i++) {
            paymentRepository.save(payments.get(i), orders.get(i));
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertSame(payments.get(1).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (int i = 0; i < payments.size(); i++) {
            paymentRepository.save(payments.get(i), orders.get(i));
        }

        Payment findResult = paymentRepository.findById("asdf");
        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        for (int i = 0; i < payments.size(); i++) {
            paymentRepository.save(payments.get(i), orders.get(i));
        }

        List<Payment> findAllResult = paymentRepository.findAll();
        assertEquals(payments.size(), findAllResult.size());
    }
}
