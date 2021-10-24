package name.shokred;

import java.util.Set;

public class PriceAggregator {

    private final PriceRetriever priceRetriever;
    private final Set<Long> shopIds;

    public PriceAggregator(final PriceRetriever priceRetriever, final Set<Long> shopIds) {
        this.priceRetriever = priceRetriever;
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        throw new UnsupportedOperationException();
    }
}
