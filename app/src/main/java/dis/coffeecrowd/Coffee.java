package dis.coffeecrowd;


import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class Coffee implements Serializable {

    public final Integer id;
    public final String name;
    public final Double price;
    public final Double averageTaste;
    public final Double averageSize;
    public final Double averageRoast;

    /**
     * This is class for one coffee
     */
    public final Class<? extends AppCompatActivity> activityClass;

    public Coffee(
            Integer id, String name, Double price, Double averageTaste, Double averageSize,
            Double averageRoast, Class<? extends AppCompatActivity> activityClass) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.averageTaste = averageTaste;
        this.averageSize = averageSize;
        this.averageRoast = averageRoast;
        this.activityClass = activityClass;
    }
}


