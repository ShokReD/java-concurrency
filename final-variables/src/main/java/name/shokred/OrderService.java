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
        final Order currentOrder = currentOrders.get(cartId);
        final Order newOrder = currentOrder.withPaymentInfo(paymentInfo);
        currentOrders.put(cartId, newOrder.checkStatus() ? deliveredOrder(newOrder) : newOrder);
    }

    public void setPacked(final long cartId) {
        final Order currentOrder = currentOrders.get(cartId);
        final Order newOrder = currentOrder.withPacked(true);
        currentOrders.put(cartId, newOrder.checkStatus() ? deliveredOrder(newOrder) : newOrder);
    }

    private Order deliveredOrder(final Order order) {
        deliver(order);
        return order.withStatus(Status.DELIVERED);
    }

    private void deliver(final Order order) { /*...*/ }
}
