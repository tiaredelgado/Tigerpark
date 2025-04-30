package com.csci3397.finalproject.Tigerpark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class VisitorActivity extends AppCompatActivity {

    private LinearLayout lotContainer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        lotContainer = findViewById(R.id.lotContainer);
        backButton = findViewById(R.id.btnBack);

        Map<String, String> lotDescriptions = buildLotDescriptions();

        for (String lot : lotDescriptions.keySet()) {
            Button lotButton = new Button(this);
            lotButton.setText(lot);
            lotButton.setAllCaps(false);

            lotButton.setOnClickListener(v -> showLotDialog(lot, lotDescriptions.get(lot)));

            lotContainer.addView(lotButton);
        }

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(VisitorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private Map<String, String> buildLotDescriptions() {
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("Lot Z", "Visitors and Staff Parking.");
        descriptions.put("Lot G", "Faculty Parking, Staff, Visitors.");
        descriptions.put("Lot D", "Staff, Visitors to Holt Conference Center.");
        descriptions.put("Laurie Parking Garage", "Faculty, Staff, Students (Orange Level), Visitors.");
        descriptions.put("Lot B", "Faculty Overflow, Staff, Students, Visitors.");
        descriptions.put("Alamo Stadium", "Faculty/Staff/Visitor Parking.\nPermit required.");
        return descriptions;
    }

    private void showLotDialog(String lotName, String description) {
        TextView messageView = new TextView(this);
        messageView.setText(description);
        messageView.setTextSize(18);
        messageView.setPadding(40, 30, 40, 30);

        new AlertDialog.Builder(this)
                .setTitle(lotName)
                .setView(messageView)
                .setPositiveButton("Directions", (dialog, which) -> {
                    Intent intent = new Intent(VisitorActivity.this, MapActivity.class);
                    double[] coords = getCoordinatesForLot(lotName);
                    intent.putExtra("LAT", coords[0]);
                    intent.putExtra("LNG", coords[1]);
                    startActivity(intent);
                })
                .setNegativeButton("Back", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private double[] getCoordinatesForLot(String lotName) {
        switch (lotName) {
            case "Lot Z":
                return new double[]{29.455600, -98.476900};
            case "Lot G":
                return new double[]{29.459750, -98.481200};
            case "Lot D":
                return new double[]{29.460580, -98.482300};
            case "Laurie Parking Garage":
                return new double[]{29.462250, -98.483270};
            case "Lot B":
                return new double[]{29.461450, -98.482950};
            case "Alamo Stadium":
                return new double[]{29.463194, -98.480661};
            default:
                return new double[]{29.462250, -98.483270}; // Default: Laurie Garage
        }
    }
}
