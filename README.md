# TigerPark - Trinity University Parking Navigation App

TigerPark is an Android application built to help Trinity University faculty, students, and visitors find available parking lots on campus and get real-time driving directions to those lots using embedded Google Maps.

---

## 🧭 Features

### 1. Role-based Landing Page

* Users are classified as:

    * Faculty
    * Student
    * Guest
* Each role has a unique list of permitted parking lots.
* The home screen offers navigation to the respective role's parking page.

### 2. Parking Lot Selection

* Users select a parking lot from a list (based on role).
* Tapping a lot name opens a dialog with:

    * Parking instructions
    * Access rules
    * A "Directions" button to navigate to the lot

### 3. Embedded Google Maps with Live Navigation

* Once "Directions" is clicked:

    * The map opens within the app (no need to launch Google Maps externally).
    * The app requests real-time location using `FusedLocationProviderClient`
    * The selected lot’s coordinates are passed via intent.
    * A car icon marker follows the user’s real-time movement.

### 4. Route Drawing with Directions API

* Directions API is called using the current location and destination.
* Response includes:

    * Polyline route: decoded and drawn as a blue line
    * Step-by-step turn instructions: parsed and listed
    * Estimated travel time (ETA) and distance: displayed

---

## 🧩 Project Structure

```bash
├── MainActivity.java               # Landing screen with role buttons
├── FacultyActivity.java            # Displays faculty-specific lots
├── StudentActivity.java            # Displays student-specific lots
├── GuestActivity.java              # Displays guest-specific lots
├── MapActivity.java                # Displays map, directions, car tracking
├── res/layout
│   ├── activity_main.xml           # Main UI layout
│   ├── activity_faculty.xml        # UI for faculty lot list
│   ├── activity_student.xml        # UI for student lot list
│   ├── activity_guest.xml          # UI for guest lot list
│   └── activity_maps.xml           # Embedded map + instruction view
├── drawable/
│   └── car_icon.png                # Custom car marker
```

---

## 🔧 API Usage

### 🗺 Google Maps SDK for Android

Used to display the embedded interactive map:

```xml
<meta-data
  android:name="com.google.android.geo.API_KEY"
  android:value="YOUR_API_KEY" />
```

### 🚘 Google Directions API

Used to get real-time driving directions:

```http
https://maps.googleapis.com/maps/api/directions/json?origin=...&destination=...&key=YOUR_API_KEY
```

#### JSON Parsing Includes:

* `overview_polyline.points`: the path
* `legs[].duration.text` and `distance.text`: travel info
* `steps[].html_instructions`: turn-by-turn steps

---

## 🔐 Permissions Required

<pre```text 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> 
<uses-permission android:name="android.permission.INTERNET" />```</pre>

---

## ⚙️ Libraries Used

* **Google Maps SDK**: Display map and markers
* **Google Directions API**: Fetch driving routes
* **FusedLocationProviderClient**: Real-time GPS tracking
* **OkHttp**: HTTP requests for Directions API
* **PolyUtil (Maps Utils)**: Decode encoded polylines

---

## 💬 Notes & Tips

* The Directions API requires **billing to be enabled**.
* Make sure to replace `YOUR_API_KEY` with your **authorized key** in both the manifest and code.
* You can customize the car icon by scaling a bitmap in Java.

---

## ✅ How It Works (Step-by-Step)

1. User opens TigerPark and selects their role.
2. A new screen lists lots they can access.
3. User clicks a lot → sees guidelines → taps “Directions.”
4. `MapActivity` opens and requests GPS permission.
5. FusedLocationProviderClient fetches current location.
6. App calls Directions API → draws route using decoded polyline.
7. Step-by-step directions are listed below map.
8. ETA and distance are shown.
9. Car marker updates position as the user moves.
10. User can click “Back” to select another lot.

---

## 📌 Developer Tips

* Ensure Google Maps and Directions APIs are **enabled** in Google Cloud Console.
* Set up **location permissions** in the manifest **and** at runtime.
* Monitor `Log.d("DirectionsAPI", ...)` for full URL troubleshooting.

---

## 🚀 Future Improvements

* Parking availability status
* Voice navigation
* Indoor parking map
* Saved lot history

---
