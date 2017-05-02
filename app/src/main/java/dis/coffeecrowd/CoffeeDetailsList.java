package dis.coffeecrowd;


/**
 * A list of all the demos we have available.
 */
public final class CoffeeDetailsList {

    /** This class should not be instantiated. */
    private CoffeeDetailsList() {
    }

    public static final Coffee[] COFFEES = {
            new Coffee(1,
                    "Tumma mokka",
                    1.50,
                    1.8,
                    4.3,
                    2.7,
                    RateCoffeeActivity.class),
            new Coffee(2,
                    "Presidentti",
                    1.50,
                    2.0,
                    4.3,
                    3.6,
                    RateCoffeeActivity.class)
    };
}
