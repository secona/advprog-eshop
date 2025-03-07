package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public static boolean contains(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
