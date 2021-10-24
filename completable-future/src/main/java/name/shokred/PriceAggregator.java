package name.shokred;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class PriceAggregator {

    private final PriceRetriever priceRetriever;
    private final Set<Long> shopIds;

    public PriceAggregator(final PriceRetriever priceRetriever, final Set<Long> shopIds) {
        this.priceRetriever = priceRetriever;
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        return shopIds.stream()
                .map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId)))
                .collect(collectingAndThen(toList(), this::getMinReceivedPrice));
    }

    private Double getMinReceivedPrice(List<CompletableFuture<Double>> list) {
        try {
            CompletableFuture
                    .allOf(list.toArray(new CompletableFuture[0]))
                    .get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ignored) { // NOSONAR
        }

        // проходим по списку всех отправленных запросов
        return list.stream()
                // оставляем только те, которые сейчас выполнены
                .filter(CompletableFuture::isDone)
                // выдергиваем из них результат
                .mapToDouble(CompletableFuture::join)
                // находим минимальный
                .min()
                .orElse(Double.NaN);
    }
}
