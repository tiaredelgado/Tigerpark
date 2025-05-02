package com.csci3397.finalproject.Tigerpark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class GuestActivity extends AppCompatActivity {

    private LinearLayout lotContainer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        lotContainer = findViewById(R.id.lotContainerGuest);
        backButton = findViewById(R.id.btnBackGuest);

        Map<String, double[]> lotCoordinates = getLotCoordinates();

        for (String lotName : lotCoordinates.keySet()) {
            Button lotButton = new Button(this);
            lotButton.setText(lotName);
            lotButton.setAllCaps(false);

            lotButton.setOnClickListener(v -> {
                Intent intent = new Intent(GuestActivity.this, MapActivity.class);
                intent.putExtra("LAT", lotCoordinates.get(lotName)[0]);
                intent.putExtra("LNG", lotCoordinates.get(lotName)[1]);
                startActivity(intent);
            });

            lotContainer.addView(lotButton);
        }

        backButton.setOnClickListener(v -> finish());
    }

    private Map<String, double[]> getLotCoordinates() {
        Map<String, double[]> map = new HashMap<>();
        map.put("Alamo Stadium", new double[]{29.455654, -98.479585});
        map.put("Laurie Parking Garage", new double[]{29.462250, -98.483270});
        map.put("Lot B", new double[]{29.464984913833216, -98.48146198251217});
        map.put("Lot C", new double[]{29.464102, -98.484260}); //accurate
        map.put("Lot G", new double[]{29.465773, -98.484330});
        map.put("Lot S", new double[]{29.460553, -98.482822});
        map.put("Lot V", new double[]{29.458791, -98.485325});
        // Add others...
        return map;
    }
}
