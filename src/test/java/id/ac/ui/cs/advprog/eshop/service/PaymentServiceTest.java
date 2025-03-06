package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Order> orders;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("3e8c353b-ff26-4dae-b925-fb7bd7c807f6");
        product1.setProductName("Kaoru Hana wa Rin to Saku Vol. 1");
        product1.setProductQuantity(1);

        products.add(product1);

        orders = new ArrayList<>();

        Order order1 = new Order("d4bfe9c1-c0da-4b94-bda4-7921c451ac14", products, 1708560000L, "Guts");
        Order order2 = new Order("f55e5029-9f30-43e7-87b0-7e979f392c24", products, 1708560000L, "Guts");

        orders.add(order1);
        orders.add(order2);

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
    void testGetPaymentFound() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
        assertSame(result, payment);
    }

    @Test
    void testGetPaymentNotFound() {
        Payment payment = payments.getFirst();
        doReturn(null).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).findAll();
        assertSame(result, payments);
    }

    @Test
    void testAddPayment() {
        Order order = orders.getFirst();
        Payment payment = payments.getFirst();

        doReturn(payment).when(paymentRepository).save(any(), any());

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());
        verify(paymentRepository, times(1)).save(payment, order);
        assertEquals(result.getId(), payment.getId());
    }

    @Test
    void testSetStatus() {
        Order order = orders.getFirst();
        Payment payment = payments.getFirst();

        doReturn(payment).when(paymentRepository).save(any());

        paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());
        paymentService.setStatus(payment, "REJECTED");
        verify(paymentRepository, times(1)).save(payment, order);
        verify(paymentRepository, times(1)).save(payment);
    }
}
