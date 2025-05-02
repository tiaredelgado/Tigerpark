package com.csci3397.finalproject.Tigerpark;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.*;

import java.io.IOException;
import java.util.*;

import okhttp3.*;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    // Declare map-related variables
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private double destinationLat;
    private double destinationLng;

    // Route line on the map
    private Polyline currentPolyline;

    // Car icon marker
    private Marker carMarker;

    // Displays ETA and distance
    private TextView infoText;

    // Displays turn-by-turn instructions
    private LinearLayout stepsContainer;

    // HTTP client for API call
    private final OkHttpClient client = new OkHttpClient();

    private static final int LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Get destination coordinates from Intent
        destinationLat = getIntent().getDoubleExtra("LAT", 0);
        destinationLng = getIntent().getDoubleExtra("LNG", 0);

        // Initialize UI elements
        infoText = findViewById(R.id.infoText);
        stepsContainer = findViewById(R.id.stepsContainer);
        Button btnBack = findViewById(R.id.btnBackMap);
        btnBack.setOnClickListener(v -> finish());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Load map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    // Called when the map is ready to be used
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Enable blue location dot and start location updates
        mMap.setMyLocationEnabled(true);
        startLocationUpdates();
    }

    // Continuously update current location and fetch new directions
    private void startLocationUpdates() {
        LocationRequest request = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setMinUpdateIntervalMillis(2000)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng dest = new LatLng(destinationLat, destinationLng);

                    // Update or place car marker
                    if (carMarker == null) {
                        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.car_icon);
                        Bitmap carIcon = Bitmap.createScaledBitmap(original, 64, 64, false);
                        carMarker = mMap.addMarker(new MarkerOptions().position(current).icon(BitmapDescriptorFactory.fromBitmap(carIcon)));
                    } else {
                        carMarker.setPosition(current);
                    }
                    // Request updated route
                    fetchDirections(current, dest);
                }
            }
        };
        // Request location updates if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, locationCallback, null);
        }
    }

    // Call the Google Directions API to fetch route
    private void fetchDirections(LatLng origin, LatLng dest) {
        String apiKey = "REPLACE_YOUR_API";  // Replace with your real key
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + dest.latitude + "," + dest.longitude +
                "&mode=driving&key=" + apiKey;
        Log.d("DirectionsAPI", "URL: " + url);

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MapActivity.this, "Route fetch failed", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) return;

                String body = response.body().string();
                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray routes = json.getJSONArray("routes");
                    if (routes.length() == 0) return;

                    JSONObject route = routes.getJSONObject(0);
                    String encodedPolyline = route.getJSONObject("overview_polyline").getString("points");
                    JSONObject leg = route.getJSONArray("legs").getJSONObject(0);
                    String duration = leg.getJSONObject("duration").getString("text");
                    String distance = leg.getJSONObject("distance").getString("text");

                    JSONArray steps = leg.getJSONArray("steps");
                    List<LatLng> path = PolyUtil.decode(encodedPolyline);

                    runOnUiThread(() -> {
                        // Update ETA + distance
                        infoText.setText("ETA: " + duration + "  |  Distance: " + distance);

                        // Add each step instruction to the layout
                        stepsContainer.removeAllViews();
                        for (int i = 0; i < steps.length(); i++) {
                            try {
                                String html = steps.getJSONObject(i).getString("html_instructions");
                                TextView tv = new TextView(MapActivity.this);
                                tv.setText(android.text.Html.fromHtml("→ " + html));
                                tv.setTextColor(Color.BLACK);
                                tv.setPadding(0, 10, 0, 10);
                                stepsContainer.addView(tv);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Draw updated route
                        if (currentPolyline != null) currentPolyline.remove();
                        currentPolyline = mMap.addPolyline(new PolylineOptions().addAll(path).color(Color.BLUE).width(10));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(path.get(0), 16));
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Clean up location listener
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onMapReady(mMap);
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}