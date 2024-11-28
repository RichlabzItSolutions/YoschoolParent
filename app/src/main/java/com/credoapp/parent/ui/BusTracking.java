package com.credoapp.parent.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.googleMaps.FetchURL;
import com.credoapp.parent.googleMaps.TaskLoadedCallback;
import com.credoapp.parent.model.busTracking.RandomLocationResponse;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.credoapp.parent.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusTracking extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 15 * 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 15 * 1000;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker marker, markerBus;
    private MarkerOptions markerr, markerBuss;

    private boolean permissionEnabled;
    private Polyline polyline;
    private TextView distanceTimeText;
    private boolean tripStarted = false;
    private String driverId, adminId, routeId;
    private static final String TAG = BusTracking.class.getSimpleName();
    private GoogleMap googleMap;
    private String latBus, longBus, homeLatitude, homeLongitude;
    LatLng orgLatLan, destLatLan;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bus_tracking);
        distanceTimeText = findViewById(R.id.distanceTimeText);
        getBusLocation();
        timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                getBusLocation();
            }
        };
        timer.schedule(hourlyTask, 0L, 1000 * 20);

        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(this.locationRequest);
        this.locationSettingsRequest = builder.build();


        Button callDriver = findViewById(R.id.callDriver);
        callDriver.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: 9676563796"));
            startActivity(intent);
        });


        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {


                super.onLocationResult(locationResult); // why? this. is. retarded. Android.
                Location currentLocation = locationResult.getLastLocation();

                //GPSPoint gpsPoint = new GPSPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
                homeLatitude = String.valueOf(currentLocation.getLatitude());
                homeLongitude = String.valueOf(currentLocation.getLongitude());
//                latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                Log.e(TAG, "onLocationResult: " + homeLatitude + "    ,     " + homeLongitude);
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
//                if (marker != null) {
//                    marker.remove();
//                }
//                marker = googleMap.addMarker(new MarkerOptions().position(latLng)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder_home)));


//                LatLng latLng2 = new LatLng(17.3850, 78.4867);

//                markerBus = googleMap.addMarker(new MarkerOptions().position(latLng2)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.school_bus)));

//                marker.setPosition(latLng);
//                markerBus.setPosition(latLng2);
//                googleMap.setMyLocationEnabled(true);


//                Log.e(TAG, "Location Callback results: " + latLng);


//                com.credoapp.driver.models.Location location = new com.credoapp.driver.models.Location(adminId, driverId, (float) latLng.latitude, (float) latLng.longitude);
//                CredoSource.getRestAPI().updateLocation(location).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
//                    Log.d(TAG, "onLocationResult: ");
//                }, throwable -> {
////                            throwable.printStackTrace();
//                });
            }
        };
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.e(TAG, "onPermissionsChecked: ");
                        permissionEnabled = true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Log.e(TAG, "onPermissionRationaleShouldBeShown: ,");
                        permissionEnabled = false;
                    }

                }).check();
        startLocationListener();
    }

    private String getUrl(LatLng busPosition, LatLng home, String directionMode) {

// Origin of route
        String str_origin = busPosition.latitude + "," + busPosition.longitude;
        // Destination of route
        String str_dest = home.latitude + "," + home.longitude;


        orgLatLan = new LatLng(busPosition.latitude, busPosition.longitude);
        destLatLan = new LatLng(home.latitude, home.longitude);


        // Mode
        String mode = directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service


        String url2 = " https://maps.googleapis.com/maps/api/directions/json?origin=" + str_origin + "&destination=" + str_dest + "&key=" + getString(R.string.google_key) + "&mode=" + mode;
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_key);
        Log.d(TAG, "getUrl: " + url2);
        return url2;
    }


    @Override
    public void onMapReady(GoogleMap googleMa) {
        Log.e(TAG, "onMapReady: ");
        googleMap = googleMa;

        googleMap.addMarker(markerr);
        googleMap.addMarker(markerBuss);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(orgLatLan);
        builder.include(destLatLan);
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        googleMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
            public void onCancel() {
            }

            public void onFinish() {
                CameraUpdate zout = CameraUpdateFactory.zoomBy(0);
                googleMap.animateCamera(zout);
            }
        });

//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerr.getPosition(), 11));
    }

    private void startLocationListener() {
//        if (permissionEnabled) {
        tripStarted = true;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(BusTracking.this);
        Log.e(TAG, "startLocationListener: " + locationRequest + "  ,  " + locationCallback);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//        }
    }

    private void removeLocationListener() {
        if (permissionEnabled) {
            tripStarted = false;
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }


    @Override
    public void getDistance(String distance, String duration) {


        String text = "Your child is " + distance + " far from you,\n It takes " + duration + " to reach you";

        distanceTimeText.setText(text);


        Log.i("======>", "getDistance: " + distance + "    ,     " + duration);
    }

    @Override
    public void onTaskDone(Object... values) {

        if (polyline != null) {
            polyline.remove();
        }
        polyline = googleMap.addPolyline((PolylineOptions) values[0]);
    }


    private void getBusLocation() {
        //Utils.showProgressBar(BusTracking.this);
//        ExamDateRequest request = new ExamDateRequest();
//        request.setStudentId(studentId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getRandomLocaations().enqueue(new Callback<RandomLocationResponse>() {


                @Override
                public void onResponse(@NonNull Call<RandomLocationResponse> call, @NonNull Response<RandomLocationResponse> response) {
                    Log.d(TAG, "onResponse1: " + response);
                    //Utils.hideProgressBar();
                    if (response.body() != null) {
                        getLocations(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RandomLocationResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    //Utils.hideProgressBar();
                }
            });
        } else {
            Constants.IntenetSettings(BusTracking.this);
        }
    }

    private void getLocations(RandomLocationResponse body) {

        if ("200".equals(body.getCode())) {
            latBus = body.getLocationS().get(0).getLatitude();
            longBus = body.getLocationS().get(0).getLongitude();

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.school_bus);
            BitmapDescriptor iconHome = BitmapDescriptorFactory.fromResource(R.drawable.placeholder_home);

            if (markerBuss != null) {
                if (markerBuss.isVisible()) {
                    if (googleMap != null) {
                        googleMap.clear();
                    }
                }
            }
            //LatLng latLng2 = new LatLng(Double.valueOf(latBus), Double.valueOf(longBus));
            markerBuss = new MarkerOptions().position(new LatLng(Double.valueOf(latBus), Double.valueOf(longBus))).title("bus");
            markerBuss.icon(icon);

//            markerBus = googleMap.addMarker(new MarkerOptions().position(latLng2)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.school_bus)));


            if (homeLatitude == null) return;

            markerr = new MarkerOptions().position(new LatLng(Double.valueOf(homeLatitude), Double.valueOf(homeLongitude))).title("home");
//            markerr = new MarkerOptions().position(new LatLng(17.496162, 78.404507)).title("home");
//            markerr.icon(iconHome);

//        String url = getUrl(markerBuss.getPosition(), markerr.getPosition(), "driving");
//            new FetchURL(BusTracking.this).execute(getUrl(markerBuss.getPosition(),markerr.getPosition(),"driving"),"driving");

            new FetchURL(BusTracking.this).execute(getUrl(markerBuss.getPosition(), markerr.getPosition(), "driving"), "driving");


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


    }


    @Override
    public void onBackPressed() {
        timer.cancel();
        timer.purge();
        super.onBackPressed();
    }
}
