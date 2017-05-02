package dis.coffeecrowd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dis.coffeecrowd.R.id.map;

public class MapPage extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback  {

    private GoogleMap mMap;
    private Button RateButton;
    private LatLng kioski;
    private boolean mPermissionDenied = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        //Button that opens "coffee rating" -view
        RateButton = (Button) findViewById(R.id.button_send);
        RateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRateCoffeeActivity();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kahvi-backend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final CafeService service = retrofit.create(CafeService.class);

        Call<ResponseBody> call = service.cafes();
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
               try {

                   Toast.makeText(MapPage.this,response.body().string(), Toast.LENGTH_SHORT).show();
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
    }



    private void launchRateCoffeeActivity() {
        Intent intent = new Intent(this, RateCoffeeActivity.class);
        startActivity(intent);
    }

    private void launchCoffeeListActivity() {
        Intent intent = new Intent(this, CoffeeListActivity.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Set location
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        //Add marker and move the camera there
        kioski = new LatLng(60.169925, 24.946092);
        mMap.addMarker(new MarkerOptions().position(kioski).title("R-kioski Kaisaniemi").snippet("Click to see coffees"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kioski));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchCoffeeListActivity();
                Toast.makeText(MapPage.this, "Opening Coffee listing", Toast.LENGTH_SHORT).show();


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

