package name.shokred;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceAggregatorTest {

    private final PriceAggregator priceAggregator = new PriceAggregator(
            new SlowPriceRetriever(10),
            Main.gatherShopIds(10)
    );

    @Test
    void only3ResponsesReturnedInTime() {
        final double minPrice = priceAggregator.getMinPrice(1);
        Assertions.assertEquals(0, Double.compare(minPrice, 7D));
    }
}
