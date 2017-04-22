package dis.coffeecrowd;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button RateButton;
    private LatLng kioski;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //googleMap.setOnMarkerClickListener(this);

        kioski = new LatLng(60.169925, 24.946092);
        mMap.addMarker(new MarkerOptions().position(kioski).title("R-kioski Kaisaniemi").snippet("sdsfdfg"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kioski));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchCoffeeListActivity();
                Toast.makeText(MapsActivity.this, "Opening Coffee listing", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
