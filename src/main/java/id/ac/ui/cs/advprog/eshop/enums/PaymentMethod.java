package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VOUCHER_CODE("VOUCHER_CODE"),
    CASH_ON_DELIVERY("CASH_ON_DELIVERY");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public static boolean contains(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
