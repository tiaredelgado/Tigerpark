package com.csci3397.finalproject.Tigerpark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    private LinearLayout lotContainer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        lotContainer = findViewById(R.id.lotContainerStudent);
        backButton = findViewById(R.id.btnBackStudent);

        Map<String, double[]> lotCoordinates = getLotCoordinates();

        for (String lotName : lotCoordinates.keySet()) {
            Button lotButton = new Button(this);
            lotButton.setText(lotName);
            lotButton.setAllCaps(false);

            lotButton.setOnClickListener(v -> {
                Intent intent = new Intent(StudentActivity.this, MapActivity.class);
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
        map.put("City Vista Parking", new double[]{29.458110, -98.484500});
        map.put("Laurie Parking Garage", new double[]{29.462250, -98.483270});
        map.put("Lot B", new double[]{29.464984913833216, -98.48146198251217});
        map.put("Lot O", new double[]{29.459696, -98.482795});
        map.put("Lot P", new double[]{29.461652, -98.483281}); //accurate
        map.put("Lot S", new double[]{29.460553, -98.482822});
        return map;
    }
}
