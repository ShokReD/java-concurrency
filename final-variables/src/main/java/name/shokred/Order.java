package name.shokred;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Order {
    private final long id;
    private final List<Item> items;
    private PaymentInfo paymentInfo;
    private boolean isPacked;
    private Status status;

    public Order(final long id,
                 final List<Item> items) {
        this.id = id;
        this.items = orEmpty(items);
    }

    public boolean checkStatus() {
        return !items.isEmpty() && paymentInfo != null && isPacked;
    }

    private static <T> List<T> orEmpty(final List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
