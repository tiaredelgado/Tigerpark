package com.csci3397.tigerpark;

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

        // Map each lot to its custom description
        Map<String, String> lotDescriptions = new HashMap<>();
        lotDescriptions.put("Alamo Stadium   A", "General Parking Guidelines\n" +
                "Parking in Alamo Stadium is allowed between certain hours Monday through Friday. \n\n" +
                "Parking overnight and on weekends is not allowed.\n\n" +
                "This lot is for:\n" +
                "Faculty \nStaff\nVisitors \nAccessible parking (designated spaces) \n\n" + "Directions?");
        lotDescriptions.put("Heidi Circle Parking   A", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "Heidi Circle parking is for:\n" +
                "Students \nStaff\nAccessible Parking \nFaculty (overflow parking) \n\n" + "Directions?");
        lotDescriptions.put("Laurie Parking Garage   A", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "Laurie Parking Garage is for:\n" +
                "Faculty \nStaff \nStudents (Orange Level only) \nVisitors (Orange Level only) \nAccessible Parking \n\n" + "Directions?");
        lotDescriptions.put("Lot B", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "This lot is for:\n" +
                "Students \nStaff\nVisitors (designated spaces) \nFaculty (overflow parking) \n\n" + "Directions?");
        lotDescriptions.put("Lot C", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "This lot is for:\n" +
                "Faculty\nStaff \nVisitors (designated spaces) \nLow emission vehicles (sticker required) \nFaculty (overflow parking) \n\n" + "Directions?");
        lotDescriptions.put("Lot D   A", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "This lot is for:\n" +
                "Staff \nVisitors of the Holt Conference Center \nAccessible parking (designated spaces) \nFaculty (overflow parking) \n\n" + "Directions?");
        lotDescriptions.put("Lot E   A", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "This lot is for:\n" +
                "Faculty\nStaff\nAccessible parking (designated spaces) \n\n" + "Directions?");
        lotDescriptions.put("Lot F", "Closed Due to Construction\n" +
                "Parking lot is closed due to construction for the Welcome Center and Event Hall.\n\n" +
                "Timeline:\n" +
                "June 2024 - Fall 2026 \n\n" +
                "Alternative Parking Options:\n" +
                "Laurie Auditorium, Prassel Garage, Alamo Stadium \n\n" + "Directions?");
        lotDescriptions.put("Lot G   A", "General Parking Guidelines\n" +
                "Parking is by permit only. Lot restrictions are in effect Monday-Friday, 7:30 a.m.-5 p.m.\n\n" +
                "This lot is for:\n" +
                "Faculty \nStaff\nVisitors (designated spaces) \nAccessible parking (designated spaces) \n\n" + "Directions?");
        lotDescriptions.put("Lot H", "Closed Due to Construction\n" +
                "Parking lot is closed due to construction for the Welcome Center and Event Hall.\n\n" +
                "Timeline:\n" +
                "June 2024 - Fall 2026 \n\n" +
                "Alternative Parking Options:\n" +
                "Laurie Auditorium, Prassel Garage, Alamo Stadium \n\n" + "Directions?");

        for (String lot : lotDescriptions.keySet()) {
            Button lotButton = new Button(this);
            lotButton.setText(lot);
            lotButton.setAllCaps(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            lotButton.setLayoutParams(params);

            String description = lotDescriptions.get(lot);
            lotButton.setOnClickListener(v -> {
                TextView messageView = new TextView(this);
                messageView.setText(description);
                messageView.setTextSize(18);
                messageView.setPadding(40, 30, 40, 30);

                new AlertDialog.Builder(this)
                        .setTitle(lot)
                        .setView(messageView)
                        .setPositiveButton("BACK", (dialog, which) -> dialog.dismiss())
                        .show();
            });

            lotContainer.addView(lotButton);
        }

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
