package name.shokred;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private Long id;
    private List<Item> items;
    private PaymentInfo paymentInfo;
    private boolean isPacked;
    private Status status;

    public Order(List<Item> items) {
        this.items = items;
    }

    public synchronized boolean checkStatus() {
        if (items != null && !items.isEmpty() && paymentInfo != null && isPacked) {
            status = Status.DELIVERED;
            return true;
        }
        return false;
    }

}
