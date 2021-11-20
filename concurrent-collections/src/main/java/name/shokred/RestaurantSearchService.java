package name.shokred;

import java.util.Set;

public class RestaurantSearchService {
//    private X stat;

    public Restaurant getByName(final String restaurantName) {
        addToStat(restaurantName);
        throw new UnsupportedOperationException();
    }

    public void addToStat(final String restaurantName) {
        throw new UnsupportedOperationException();
    }

    public Set<String> printStat() {
        throw new UnsupportedOperationException();
    }
}
