package edu.neag.tia.Tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.neag.tia.MainActivity;
import edu.neag.tia.R;
import edu.neag.tia.RestClient.ResPosition;
import edu.neag.tia.RestClient.client.UserService;
import edu.neag.tia.RestClient.client.Utils;
import edu.neag.tia.RestClient.dto.PositionDTO;
import edu.neag.tia.Sign.HomeActivity;
import edu.neag.tia.Sign.UpdateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Button btnGetPos, btnSavePos;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Double latitude = 0.0, longitude = 0.0;
    private Toolbar toolbar;
    private String credentials, emailIntent, lastNameFromResp, firstNameFromResp;
    private EditText etLatitude, etLongitude, etBusId;
    private Location mCurrentLocation;
    private UserService userService;
    public static TrackingActivity instance = null;
    private String busId, longitudeString, latitudeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        instance = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        credentials = extras.getString("credentials");
        emailIntent = extras.getString("email");
        lastNameFromResp = extras.getString("lastName");
        firstNameFromResp = extras.getString("firstName");
        userService = Utils.getUserService();

        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etBusId = (EditText) findViewById(R.id.etBusId);
        btnSavePos = (Button) findViewById(R.id.btnSavePos);


        toolbar = (Toolbar) findViewById(R.id.routeToolBar);
        toolbar.inflateMenu(R.menu.menu_app);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_home) {
                    openActivityFromMenu(TrackingActivity.this, HomeActivity.class, true);

                }
                if (item.getItemId() == R.id.menu_update) {
                    openActivityFromMenu(TrackingActivity.this, UpdateActivity.class, true);


                }
                if (item.getItemId() == R.id.menu_logout) {
                    if (TrackingActivity.instance != null) {
                        TrackingActivity.instance.finish();
                    }
                    if (UpdateActivity.instance != null) {
                        UpdateActivity.instance.finish();
                    }
                    if (HomeActivity.instance != null) {
                        HomeActivity.instance.finish();
                    }
                    startActivity(new Intent(TrackingActivity.this, MainActivity.class));

                }
                return false;
            }
        });

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                etLatitude.setText(latitude.toString());
                etLongitude.setText(longitude.toString());
                LatLng position = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(position).title("Bus current location might be here!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        btnSavePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busId = etBusId.getText().toString();
                longitudeString = etLongitude.getText().toString();
                latitudeString = etLatitude.getText().toString();
                if (busId.isEmpty() || longitudeString.isEmpty() || latitudeString.isEmpty()) {
                    Toast.makeText(TrackingActivity.this, "Please complete all required fields!", Toast.LENGTH_SHORT).show();
                } else {
                    doSavePosition(busId, latitudeString, longitudeString, credentials);
                }
            }
        });

        CheckPermission();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.INTERNET}, 1);
            return;
        } else {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    private void doSavePosition(String terminalId, String latitude, String longitude, String credentials) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setLatitude(latitude);
        positionDTO.setLongitude(longitude);
        positionDTO.setTerminalId(terminalId);

        Call<ResPosition> call = userService.addPosition(positionDTO, credentials);
        call.enqueue(new Callback<ResPosition>() {
            @Override
            public void onResponse(Call<ResPosition> call, Response<ResPosition> response) {

                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        ResPosition resPosition = (ResPosition) response.body();
                        Toast.makeText(TrackingActivity.this, "Success! Position saved!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TrackingActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResPosition> call, Throwable t) {
                Toast.makeText(TrackingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void openActivityFromMenu(Context packageContext, Class<?> cls, boolean flagState) {
        Intent intent = new Intent(packageContext, cls);
        if (flagState) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        intent.putExtra("credentials", credentials);
        intent.putExtra("email", emailIntent);
        intent.putExtra("lastName", lastNameFromResp);
        intent.putExtra("firstName", firstNameFromResp);
        startActivity(intent);

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        etLatitude.setText(latitude.toString());
        etLongitude.setText(longitude.toString());
        mCurrentLocation = location;
        LatLng position = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(position).title("Bus current location might be here!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}