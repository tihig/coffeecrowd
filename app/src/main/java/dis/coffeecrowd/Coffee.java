package dis.coffeecrowd;


import android.support.v7.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coffee implements Serializable {

    @SerializedName("id")
    public final Integer id;
    @SerializedName("name")
    public final String name;
    @SerializedName("price")
    public final Double price;
    @SerializedName("averageTaste")
    public final Double averageTaste;
    @SerializedName("averageSize")
    public final Double averageSize;
    @SerializedName("averageRoast")
    public final Double averageRoast;

    /**
     * This is class for one coffee
     */
    //public final Class<? extends AppCompatActivity> activityClass;

    public Coffee(
            Integer id, String name, Double price, Double averageTaste, Double averageSize,
            Double averageRoast) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.averageTaste = averageTaste;
        this.averageSize = averageSize;
        this.averageRoast = averageRoast;
        //this.activityClass = activityClass;, Class<? extends AppCompatActivity> activityClass
    }
}


