package name.shokred;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private Map<Long, Order> currentOrders = new HashMap<>();
    private long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    public synchronized long createOrder(List<Item> items) {
        long id = nextId();
        Order order = new Order(items);
        order.setId(id);
        currentOrders.put(id, order);
        return id;
    }

    public synchronized void updatePaymentInfo(long cartId, PaymentInfo paymentInfo) {
        currentOrders.get(cartId).setPaymentInfo(paymentInfo);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
            currentOrders.get(cartId).setStatus(Status.DELIVERED);
        }
    }

    public synchronized void setPacked(long cartId) {
        currentOrders.get(cartId).setPacked(true);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
        }
    }

    private synchronized void deliver(Order order) { /*...*/ }
}
