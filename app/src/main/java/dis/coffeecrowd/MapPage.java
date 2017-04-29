package dis.coffeecrowd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import static dis.coffeecrowd.R.id.map;

public class MapPage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button RateButton;
    private LatLng kioski;
    private LongPressLocationSource mLocationSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        mLocationSource = new LongPressLocationSource();

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

        // Location
        mMap.setLocationSource(mLocationSource);
        mMap.setOnMapLongClickListener(mLocationSource);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

    }
}
