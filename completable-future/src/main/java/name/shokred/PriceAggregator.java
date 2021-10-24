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
                    // Результаты JMH показали что на QuickPriceRetriever получалось
                    // от 10^(-5) до 10^(-4) секунд на 1 операцию выгрузки.
                    // Поэтому принимаем худший вариант (10_000 мкс) и вычитаем его из 3 секунд
                    .get(2_990_000, TimeUnit.MICROSECONDS);
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
