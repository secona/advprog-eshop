package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.setPaymentData(paymentData);
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

        if (address.isEmpty() || deliveryFee.isEmpty()) {
            return false;
        }

        return true;
    }
}
