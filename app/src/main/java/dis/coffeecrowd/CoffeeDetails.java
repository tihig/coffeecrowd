package dis.coffeecrowd;


import android.support.v7.app.AppCompatActivity;

public class CoffeeDetails {


    public final String title;

    public final String description;

    /**
     * This is class for one coffee
     */
    public final Class<? extends AppCompatActivity> activityClass;

    public CoffeeDetails(
            String title, String description, Class<? extends AppCompatActivity> activityClass) {
        this.title = title;
        this.description = description;
        this.activityClass = activityClass;
    }
}


