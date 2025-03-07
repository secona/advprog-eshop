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
    void testCreatePaymentByInvalidVoucherCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "VOUCHER_CODE", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "asdf"); // not 16 in length

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "VOUCHER_CODE", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidVoucherCodeEshopPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "0123456789ABCDEF"); // length 16 but no ESHOP prefix

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "VOUCHER_CODE", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidVoucherCodeNumericCount() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D"); // incorrect numeric count

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
    void testCreatePaymentByInvalidCashOnDeliveryNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "100000");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidCashOnDeliveryNullDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidCashOnDeliveryEmptyAddress() {
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
    void testCreatePaymentByInvalidCashOnDeliveryEmptyDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jakarta");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", "CASH_ON_DELIVERY", paymentData);

        assertEquals("d0ef59ff-7e69-44b6-961b-bdb3c016cc3b", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentByInvalidCashOnDeliveryBothEmptyStrings() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "");

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
