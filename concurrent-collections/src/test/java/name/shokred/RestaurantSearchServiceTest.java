package name.shokred;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Disabled("For debug goals only!")
class RestaurantSearchServiceTest {

    private final RandomList randomList = new RandomList("KFC", "McDonalds", "Burger King");
    private final RestaurantSearchService service = new RestaurantSearchService();

    @Test
    void getByNameLoaded() throws InterruptedException {
        final int threadCount = 100;
        final CountDownLatch cdl = new CountDownLatch(1);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                service.getByName(randomList.nextRandomValue());
            }).start();
        }
        cdl.countDown();
        Thread.sleep(1000L);
        System.out.println(service.printStat());
    }

    @Test
    void getByName() {
        for (int i = 0; i < 100; i++) {
            service.getByName(randomList.nextRandomValue());
        }

        System.out.println(service.printStat());
    }

    private static class RandomList {
        private final List<String> values;

        private RandomList(String... values) {
            this.values = Arrays.asList(values);
        }

        public String nextRandomValue() {
            return values.get(randomIndex());
        }

        private int randomIndex() {
            return ThreadLocalRandom.current().nextInt(values.size());
        }
    }
}
