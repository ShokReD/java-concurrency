package name.shokred;

import lombok.With;

import java.util.Collections;
import java.util.List;

@With
public class Order {
    private final long id;
    private final List<Item> items;
    private final PaymentInfo paymentInfo;
    private final boolean isPacked;
    private final Status status;

    public Order(final long id,
                 final List<Item> items) {
        this(id, orEmpty(items), null, false, null);
    }

    public Order(final long id,
                 final List<Item> items,
                 final PaymentInfo paymentInfo,
                 final boolean isPacked,
                 final Status status) {
        this.id = id;
        this.items = items;
        this.paymentInfo = paymentInfo;
        this.isPacked = isPacked;
        this.status = status;
    }

    public boolean checkStatus() {
        return !items.isEmpty() && paymentInfo != null && isPacked;
    }

    private static <T> List<T> orEmpty(final List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
