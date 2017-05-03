package dis.coffeecrowd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

import static dis.coffeecrowd.R.id.map;

public class MapPage extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback  {

    private GoogleMap mMap;
    private LatLng kioski;
    private boolean mPermissionDenied = false;
    private List<Cafe> cafes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        setTitle("Coffee Crowd - select cafe");
    }

    private void launchCoffeeListActivity(Cafe cafe) {
        Intent intent = new Intent(this,CoffeeListActivity.class);
        intent.putExtra("cafe", cafe);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Set location
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        //Setup DB fetcher
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kahvi-backend.herokuapp.com/")
                .client(client)
                .build();

        final CafeService service = retrofit.create(CafeService.class);

        Call<ResponseBody> call = service.cafes();
        //Fetching cafes from DB
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {

                    Log.d("Tassa ollaan", response.body().toString());
                    String json = response.body().string();
                    System.out.println(response.toString());
                    Log.w("Retrofit@Response", response.body().string());


                    Moshi moshi = new Moshi.Builder().build();
                    Type listMyData = Types.newParameterizedType(List.class, Cafe.class);
                    JsonAdapter<List<Cafe>> adapter = moshi.adapter(listMyData);

                    cafes = adapter.fromJson(json);
                    System.out.println(cafes.toString());

                    kioski = new LatLng((double)cafes.get(2).lat, (double) cafes.get(2).lon);
                    mMap.addMarker(new MarkerOptions().position(kioski).title(cafes.get(2).name).snippet("Click to see coffees"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(kioski.latitude, kioski.longitude), 12.0f));

                    for (int i = 0; i < (int) cafes.size(); i++) {
                        if (cafes.get(i).lat != null){
                        kioski = new LatLng((double) cafes.get(i).lat, (double) cafes.get(i).lon);
                        mMap.addMarker(new MarkerOptions().position(kioski).title(cafes.get(i).name).snippet("Click to see coffees")).setTag(cafes.get(i));

                    }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MapPage.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> _, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MapPage.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchCoffeeListActivity((Cafe) marker.getTag());
                Toast.makeText(MapPage.this, "Opening Coffee listing ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(MapPage.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}

