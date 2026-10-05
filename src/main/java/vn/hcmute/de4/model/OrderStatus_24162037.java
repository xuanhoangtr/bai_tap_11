package vn.hcmute.de4.model;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus_24162037 {
    PENDING("Đơn hàng mới", "warning"),
    CONFIRMED("Đã xác nhận", "info"),
    PREPARING("Chuẩn bị hàng", "primary"),
    SHIPPING("Vận chuyển", "primary"),
    DELIVERING("Giao hàng", "info"),
    DELIVERED("Đã giao", "success"),
    CANCELLED("Đơn hàng hủy", "danger"),
    RETURNED("Đơn hàng hoàn", "secondary");

    private final String label;
    private final String badgeColor;

    OrderStatus_24162037(String label, String badgeColor) {
        this.label = label;
        this.badgeColor = badgeColor;
    }

    public String getCode() {
        return name();
    }

    public String getLabel() {
        return label;
    }

    public String getBadgeColor() {
        return badgeColor;
    }

    public static Optional<OrderStatus_24162037> fromCode(String code) {
        if (code == null || code.isBlank()) return Optional.empty();
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(code.trim()))
                .findFirst();
    }
}
