package dis.coffeecrowd;

public class Review {

    public final Integer coffeeId;
    public final Integer reviewerId;
    public final Integer taste;
    public final Integer size;
    public final Integer roast;

    public Review(Integer coffeeId, Integer reviewerId, Integer taste, Integer size, Integer roast) {
        this.coffeeId = coffeeId;
        this.reviewerId = reviewerId;
        this.taste = taste;
        this.size = size;
        this.roast = roast;
    }
}
