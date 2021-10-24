package name.shokred;

public class SlowPriceRetriever implements PriceRetriever {

    private final int shopCount;

    public SlowPriceRetriever(final int shopCount) {
        this.shopCount = shopCount;
    }

    @Override
    public double getPrice(final long itemId, final long shopId) {
        try {
            Thread.sleep(shopId * 1000);
        } catch (InterruptedException e) { // NOSONAR
            e.printStackTrace();
        }
        return (double) shopCount - shopId;
    }
}
