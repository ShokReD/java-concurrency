package name.shokred;

import java.util.Set;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toSet;

public class Main {
    public static void main(String[] args) {

    }

    public static Set<Long> gatherShopIds(int count) {
        return LongStream.iterate(1, x -> x + 1)
                .limit(count)
                .boxed()
                .collect(toSet());
    }
}
