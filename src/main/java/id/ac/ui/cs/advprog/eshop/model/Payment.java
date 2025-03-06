package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.setMethod(method);
        this.setPaymentData(paymentData);
    }

    public void setMethod(String method) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid method: " + method);
        }

        this.method = method;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        switch (this.method) {
            case "VOUCHER_CODE":
                if (isValidVoucherCode(paymentData.get("voucherCode"))) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }

                this.paymentData = paymentData;
                break;
            case "CASH_ON_DELIVERY":
                if (isValidCOD(paymentData.get("address"), paymentData.get("deliveryFee"))) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }

                this.paymentData = paymentData;
                break;
            default:
                break;
        }
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (char ch : voucherCode.toCharArray()) {
            if (Character.isDigit(ch)) {
                numericCount++;
            }
        }

        return numericCount == 8;
    }

    private boolean isValidCOD(String address, String deliveryFee) {
        if (address == null || deliveryFee == null) {
            return false;
        }

        return !address.isEmpty() && !deliveryFee.isEmpty();
    }
}
