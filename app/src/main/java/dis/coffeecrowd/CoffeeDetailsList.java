package dis.coffeecrowd;


/**
 * A list of all the demos we have available.
 */
public final class CoffeeDetailsList {

    /** This class should not be instantiated. */
    private CoffeeDetailsList() {
    }

    public static final CoffeeDetails[] COFFEES = {
            new CoffeeDetails("Tumma mokka",
                    "Tumma ja paahteinen kahvielämys",
                    RateCoffeeActivity.class)
    };
}
