package dis.coffeecrowd;


import android.support.v7.app.AppCompatActivity;

public class CoffeeDetails {
    /**
     * The resource id of the title of the demo.
     */
    public final String title;

    /**
     * The resources id of the description of the demo.
     */
    public final int descriptionId;

    /**
     * The demo activity's class.
     */
    public final Class<? extends AppCompatActivity> activityClass;

    public CoffeeDetails(
            String title, int descriptionId, Class<? extends AppCompatActivity> activityClass) {
        this.title = title;
        this.descriptionId = descriptionId;
        this.activityClass = activityClass;
    }
}


