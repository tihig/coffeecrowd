package dis.coffeecrowd;


import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public final class CoffeeDetailsList {
    private List<Coffee> coffees;


    /** This class should not be instantiated. */
    private CoffeeDetailsList() {

    }


    public static final Coffee[] COFFEES = {
            new Coffee(1,
                    "Tumma mokka",
                    1.50,
                    1.8,
                    4.3,
                    2.7),
            new Coffee(2,
                    "Presidentti",
                    1.50,
                    2.0,
                    4.3,
                    3.6
                    )
    };
}
