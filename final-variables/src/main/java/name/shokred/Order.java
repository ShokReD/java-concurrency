package name.shokred;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Order {
    private Long id;
    private final List<Item> items;
    private PaymentInfo paymentInfo;
    private boolean isPacked;
    private Status status;

    public Order(final List<Item> items) {
        this.items = orEmpty(items);
    }

    public synchronized boolean checkStatus() {
        if (!items.isEmpty() && paymentInfo != null && isPacked) {
            status = Status.DELIVERED;
            return true;
        }
        return false;
    }

    private static <T> List<T> orEmpty(final List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
