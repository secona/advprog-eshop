package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class,
                () -> new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "INVALID", paymentData));
    }

    @Test
    void testCreatePaymentByValidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "VOUCHER_CODE", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "asdf");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "VOUCHER_CODE", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByValidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidCashOnDeliveryEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testUpdatePaymentUsingValidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testUpdatePaymentUsingInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("ASDF"));
    }
}
