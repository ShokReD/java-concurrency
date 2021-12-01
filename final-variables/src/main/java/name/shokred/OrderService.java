package name.shokred;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class OrderService {

    private final Map<Long, Order> currentOrders = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(0L);

    private long nextId() {
        return nextId.getAndIncrement();
    }

    public long createOrder(final List<Item> items) {
        final long id = nextId();
        final Order order = new Order(id, items);
        currentOrders.put(id, order);
        return id;
    }

    public void updatePaymentInfo(final long cartId, final PaymentInfo paymentInfo) {
        currentOrders.computeIfPresent(cartId, (id, order) -> {
            order.setPaymentInfo(paymentInfo);
            if (order.checkStatus()) {
                deliver(order);
                order.setStatus(Status.DELIVERED);
            }
            return order;
        });
    }

    public void setPacked(final long cartId) {
        currentOrders.computeIfPresent(cartId, (id, order) -> {
            order.setPacked(true);
            if (order.checkStatus()) {
                deliver(order);
                order.setStatus(Status.DELIVERED);
            }
            return order;
        });
    }

    private void deliver(final Order order) { /*...*/ }
}
