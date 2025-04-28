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

public class FacultyActivity extends AppCompatActivity {

    private LinearLayout lotContainer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

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
            Intent intent = new Intent(FacultyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private Map<String, String> buildLotDescriptions() {
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("Alamo Stadium", "Faculty/Staff/Visitor Parking.\nPermit required.");
        descriptions.put("Heidi Circle Parking", "Faculty Overflow, Staff, Student Parking.");
        descriptions.put("Laurie Parking Garage", "Faculty, Staff, Students (Orange Level), Visitors.");
        descriptions.put("Lot B", "Faculty Overflow, Staff, Students, Visitors.");
        descriptions.put("Lot C", "Faculty Parking, Staff, Visitors, Low Emission Vehicles.");
        descriptions.put("Lot D", "Staff, Visitors to Holt Conference Center.");
        descriptions.put("Lot E", "Faculty Parking, Staff, Accessible Parking.");
        descriptions.put("Lot F", "Closed for construction (2024–2026).");
        descriptions.put("Lot G", "Faculty Parking, Staff, Visitors.");
        descriptions.put("Lot H", "Closed for construction (2024–2026).");
        descriptions.put("Lot L", "Faculty and Staff Parking.");
        descriptions.put("Lot M", "Faculty and Staff Parking.");
        descriptions.put("Lot O", "Faculty Overflow Parking, Staff, Students.");
        descriptions.put("Lot P", "Faculty Overflow Parking, Staff, Students.");
        descriptions.put("Lot Q", "Faculty and Staff Parking.");
        descriptions.put("Lot R", "Faculty and Staff Parking.");
        descriptions.put("Lot S", "Students, Staff, Visitors.");
        descriptions.put("Lot T", "Students and Staff.");
        descriptions.put("Lot U", "Students, Staff, Visitors.");
        descriptions.put("Prassel Garage", "Faculty, Staff, Prassel Residents.");
        descriptions.put("Lot W", "Students and Staff Parking.");
        descriptions.put("Lot X", "Students and Staff Parking.");
        descriptions.put("Lot Y", "Students and Staff Parking.");
        descriptions.put("Lot Z", "Visitors and Staff Parking.");
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
                    Intent intent = new Intent(FacultyActivity.this, MapActivity.class);
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
            case "Alamo Stadium":
                return new double[]{29.463194, -98.480661};
            case "Heidi Circle Parking":
                return new double[]{29.463710, -98.483560};
            case "Laurie Parking Garage":
                return new double[]{29.462250, -98.483270};
            case "Lot B":
                return new double[]{29.461450, -98.482950};
            case "Lot C":
                return new double[]{29.461050, -98.482600};
            case "Lot D":
                return new double[]{29.460580, -98.482300};
            case "Lot E":
                return new double[]{29.460130, -98.481900};
            case "Lot F":
                return new double[]{29.459800, -98.481500};
            case "Lot G":
                return new double[]{29.459750, -98.481200};
            case "Lot H":
                return new double[]{29.459400, -98.480800};
            case "Lot L":
                return new double[]{29.459200, -98.480500};
            case "Lot M":
                return new double[]{29.458900, -98.480200};
            case "Lot O":
                return new double[]{29.458600, -98.479900};
            case "Lot P":
                return new double[]{29.458300, -98.479600};
            case "Lot Q":
                return new double[]{29.458000, -98.479300};
            case "Lot R":
                return new double[]{29.457700, -98.479000};
            case "Lot S":
                return new double[]{29.457400, -98.478700};
            case "Lot T":
                return new double[]{29.457100, -98.478400};
            case "Lot U":
                return new double[]{29.456800, -98.478100};
            case "Prassel Garage":
                return new double[]{29.462000, -98.482500};
            case "Lot W":
                return new double[]{29.456500, -98.477800};
            case "Lot X":
                return new double[]{29.456200, -98.477500};
            case "Lot Y":
                return new double[]{29.455900, -98.477200};
            case "Lot Z":
                return new double[]{29.455600, -98.476900};
            default:
                return new double[]{29.462250, -98.483270}; // Default Laurie Garage
        }
    }
}
