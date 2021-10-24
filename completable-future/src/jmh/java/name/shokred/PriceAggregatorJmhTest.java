package name.shokred;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 10, time = 2)
@State(Scope.Benchmark)
@Timeout(time = 1)
public class PriceAggregatorJmhTest {

    private final PriceAggregator priceAggregator = new PriceAggregator(
            new QuickPriceRetriever(),
            Main.gatherShopIds(10)
    );

    @Benchmark
    public void aggregating(final Blackhole blackhole) {
        final double minPrice = priceAggregator.getMinPrice(1);
        blackhole.consume(minPrice);
    }
}
