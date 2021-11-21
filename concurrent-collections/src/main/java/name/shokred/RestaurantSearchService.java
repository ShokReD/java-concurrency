package name.shokred;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

public class RestaurantSearchService {
    private final Map<String, Integer> stat = new ConcurrentHashMap<>();

    public Restaurant getByName(final String restaurantName) {
        addToStat(restaurantName);
        return new Restaurant();
    }

    public void addToStat(final String restaurantName) {
        this.stat.merge(restaurantName, 1, Integer::sum);
    }

    public Set<String> printStat() {
        return this.stat.entrySet().stream()
                .map(entry -> String.format("%s - %s", entry.getKey(), entry.getValue()))
                .collect(toSet());
    }
}
