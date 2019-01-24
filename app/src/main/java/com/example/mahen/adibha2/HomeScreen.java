package com.example.mahen.adibha2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.mahen.adibha2.Preferences.PrefsManager;

import static com.example.mahen.adibha2.Preferences.PrefsManager.ID_KEY;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_EMAIL;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_NAME;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_PHONE;
import static com.example.mahen.adibha2.Preferences.PrefsManager.USER_PREFS;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, PlaceSelectionListener, NavigationView.OnNavigationItemSelectedListener, BookBottomSheet.BottomSheetListener {


    SharedPreferences userpref;
//    TextView userTxt, useridTxt, phoneTxt, emailTxt;

    //get pref variables
    String user,idd,phone,emaill;

    //variable declaration rk
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(11.398194, 79.695358), new LatLng(11.916064, 79.812325));
    private static final int REQUEST_SELECT_PICK = 1000;
    public static final String MESSAGE = "OPTION";

    int pickP = 0, dropP = 0;

    //map utils
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient, mGoogleApiClient1;
    MarkerOptions options = new MarkerOptions();
    private Marker oriMarker, destMarker;
    LocationRequest mLocationRequest;
    LatLng origin, dest;
    //Type Variable says about rent or city or out
    String type;
    public static final String LOG_TAG = "MapsActivity";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //rk adiba global declarations
    private DrawerLayout drawer;
    Boolean back = false;
    boolean isUp;
    View myView;
    BottomNavigationView bottomNavigationView;
    LinearLayout travel_type;
    Button ridelater;


    //Place autocomplete elements
    TextView pickupLocTxt, dropLocTxt;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //setting preferences
        PrefsManager prefsManager = new PrefsManager(getApplicationContext());
        prefsManager.setFirstTimeLaunch(true);

        //components initialization
//        useridTxt = findViewById(R.id.useridTxt);
//        userTxt = findViewById(R.id.userTxt);
//        phoneTxt = findViewById(R.id.userPhoneTxt);
//        emailTxt = findViewById(R.id.userEmailTxt);

        //geting sharedpreferences
        getPrefsdetails();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        dropLocTxt = findViewById(R.id.dropLocation);
        pickupLocTxt = findViewById(R.id.pickupLocation);


        //hiding the drop textbox on default
        dropLocTxt.setVisibility(View.GONE);

        mContext = HomeScreen.this;

        //declarations
        myView = findViewById(R.id.navigationView);
        myView.setVisibility(View.GONE);
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setVisibility(View.GONE);
        ridelater = findViewById(R.id.button3);
        travel_type = findViewById(R.id.btn_nav_bar);


        //map integeration goes here

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        MarkerPoints = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        View mapView = mapFragment.getView();
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 100);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(10, 10, 10, 10);
        }
        mapFragment.getMapAsync(this);

        //onclick listeners

        final CardView toolbar = findViewById(R.id.toolbar);

//important
        // View present inside drawer layout

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ImageButton nav_button = findViewById(R.id.nav_button);
        nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
            }
        });


        drawer = findViewById(R.id.drawer_layout);
        toolbar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                drawer.openDrawer(toolbar);
                return true;
            }
        });


        //place autocomplete
        mGoogleApiClient1 = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {
//                Says about the Visibility of Cardview & Recycler View
                if (isUp) {
                    isUp = false;
                    slideDown2(myView);
                }
                if (travel_type.getVisibility() == View.VISIBLE)
                    travel_type.setVisibility(View.GONE);

            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
//                if (myView.getVisibility() == View.GONE)
//                    myView.setVisibility(View.VISIBLE);
                if (travel_type.getVisibility() == View.GONE)
                    travel_type.setVisibility(View.VISIBLE);
            }
        });

        //working code rk

//       googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//           @Override
//           public void onMapClick(LatLng latLng) {
////               mMap.addMarker(new MarkerOptions()
////                       .position(latLng)
////                       .title("My Spot")
////                       .snippet("This is my spot!")
////                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
////               if (oriMarker != null) {
////                   oriMarker.remove();
////               }
////                   MarkerOptions markerOptionsOri = new MarkerOptions();
////                   markerOptionsOri.position(latLng);
////                   markerOptionsOri.title("Starting point");
////                   markerOptionsOri.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
////                   oriMarker = mMap.addMarker(markerOptionsOri);
//           }
//       });


//important
        //map theme
//        try {
//            boolean success = mMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(this, R.raw.raw_style));
//            if (!success) {
//                // Handle map style load failure
//            }
//        } catch (Resources.NotFoundException e) {
//            // Oops, looks like the map style resource couldn't be found!
//        }

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                LatLng position = marker.getPosition();
                double markerlat = position.latitude;
                double markerlong = position.longitude;
                Geocoder geocoder;
                List<Address> addresses;
                String address = "";
                geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(markerlat, markerlong, 1);
                    address = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeScreen.this, "Can't get Address", Toast.LENGTH_SHORT).show();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (marker.getTitle().equalsIgnoreCase("Starting Point")) {
                    oriMarker.remove();

                    MarkerPoints.remove(0);
                    MarkerPoints.add(0, marker.getPosition());
                    origin = MarkerPoints.get(0);
                    pickupLocTxt.setText(address);

                } else {
                    destMarker.remove();
                    MarkerPoints.remove(1);
                    dropLocTxt.setText(address);
                    MarkerPoints.add(1, marker.getPosition());
                    dest = MarkerPoints.get(1);
                }
            }
        });

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    public void getPrefsdetails() {
        userpref = getSharedPreferences(USER_PREFS, MODE_PRIVATE);

        user = userpref.getString(USER_NAME,"");
        idd = userpref.getString(ID_KEY,"");
        phone = userpref.getString(USER_EMAIL,"");
        emaill = userpref.getString(USER_PHONE,"");
//
//        userTxt.setText(user);
//        useridTxt.setText(idd);
//        phoneTxt.setText(phone);
//        emailTxt.setText(emaill);
        Log.i(LOG_TAG,"prefs:"+idd+" "+user+" "+phone+" "+emaill);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_book:
                Toast.makeText(getApplicationContext(), "Book Option Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Intent intent1 = new Intent(this, about.class);
                startActivity(intent1);
                break;
            case R.id.nav_your_rides:
                Intent intent = new Intent(this, YourRides.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Your Rides Option Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_support:
                Toast.makeText(getApplicationContext(), "Support Option Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:

//                TwoStageRate twoStageRate = TwoStageRate.with(this);
//
//                twoStageRate.showRatePromptDialog();
//                twoStageRate.setFeedbackWithRatingReceivedListener(new FeedbackWithRatingReceivedListener() {
//                    @Override
//                    public void onFeedbackReceived(float rating, String feedback) {
//                        Toast.makeText(HomeScreen.this, "Rating :" + rating + "Feedback :" + feedback, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Toast.makeText(getApplicationContext(), "Feedback Option Selected", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        options = new MarkerOptions();

        // Setting the position of the marker
        options.position(latLng);

        //move map camerax
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onError(Status status) {

    }

    private void slideDown2(View myView) {
        myView.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, myView.getHeight());
        myView.startAnimation(animate);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is neededx
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);
            switch (view.getId()) {
                case R.id.rental:
                    disableAuto(false);
                    type = "rental";
                    dropVisiblity(type);
                    getnavigation(type);
                    break;
                case R.id.city:
                    disableAuto(true);
                    type = "city";
                    dropVisiblity(type);
                    getCitynavigation(type);
                    break;
                case R.id.outstation:
                    disableAuto(true);
                    type = "outstation";
                    dropVisiblity(type);
                    getOutstation(type);
                    break;

            }

        } else {
            slideUp(myView);
            switch (view.getId()) {
                case R.id.rental:
                    disableAuto(false);
                    type = "rental";
                    dropVisiblity(type);
                    getnavigation(type);
                    break;
                case R.id.city:
                    disableAuto(true);
                    type = "city";
                    dropVisiblity(type);
                    getCitynavigation(type);
                    break;
                case R.id.outstation:
                    disableAuto(true);
                    type = "outstation";
                    dropVisiblity(type);
                    getOutstation(type);
                    break;
            }

        }
        isUp = !isUp;
    }

    private void getOutstation(String typea) {

        Log.i("my_tag", "Welcome");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("my_tag", "Welcome2");
                switch (menuItem.getTitle().toString()) {

                    case "Prime":
                        outstationBottomSheet bottomSheet1 = new outstationBottomSheet();
                        Bundle bundle = new Bundle();
                        bundle.putString("pickn", pickupLocTxt.getText().toString());
                        bundle.putString("dropn", dropLocTxt.getText().toString());

//                        bundle.putString("ori_lat", String.valueOf(origin.latitude));
//                        bundle.putString("ori_lng", String.valueOf(origin.longitude));
//                        bundle.putString("dest_lat", String.valueOf(dest.latitude));
//                        bundle.putString("dest_lng", String.valueOf(dest.longitude));

                        bundle.putString("vehicle", "Prime");
                        bundle.putString("travel_type", type);
                        bottomSheet1.setArguments(bundle);
                        bottomSheet1.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;

                    case "SUV":
                        outstationBottomSheet bottomSheet2 = new outstationBottomSheet();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("pickn", pickupLocTxt.getText().toString());
                        bundle1.putString("dropn", dropLocTxt.getText().toString());

//                        bundle1.putString("ori_lat", String.valueOf(origin.latitude));
//                        bundle1.putString("ori_lng", String.valueOf(origin.longitude));
//                        bundle1.putString("dest_lat", String.valueOf(dest.latitude));
//                        bundle1.putString("dest_lng", String.valueOf(dest.longitude));

                        bundle1.putString("vehicle", "SUV");
                        bundle1.putString("travel_type", type);
                        bottomSheet2.setArguments(bundle1);
                        bottomSheet2.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;
                }
                return true;
            }
        });

    }

    private void dropVisiblity(String type) {
        switch (type) {
            case "rental":
                if (dropLocTxt.getVisibility() == View.VISIBLE)
                    dropLocTxt.setVisibility(View.GONE);
                break;
            case "city":
                if (dropLocTxt.getVisibility() == View.GONE)
                    dropLocTxt.setVisibility(View.VISIBLE);
                break;
            case "outstation":
                if (dropLocTxt.getVisibility() == View.GONE)
                    dropLocTxt.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void disableAuto(boolean val) {
        bottomNavigationView.getMenu().getItem(0).setEnabled(val);
        bottomNavigationView.getMenu().findItem(R.id.navigation_songs).setIcon(R.drawable.ic_auto_disabled);
    }

    private void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void getnavigation(String typeof) {
        Log.i("my_tag", "Welcome");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("my_tag", "Welcome2");
                switch (menuItem.getTitle().toString()) {
                    case "Auto":
                        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

                        alertDialog.setMessage("A");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    case "Prime":
                        BookBottomSheet bottomSheet1 = new BookBottomSheet();
                        Bundle bundle = new Bundle();
                        bundle.putString("pickn", pickupLocTxt.getText().toString());
                        bundle.putString("vehicle", "Prime");
                        bundle.putString("travel_type", type);
                        bottomSheet1.setArguments(bundle);
                        bottomSheet1.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;

                    case "SUV":
                        BookBottomSheet bottomSheet2 = new BookBottomSheet();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("pickn", pickupLocTxt.getText().toString());
                        bundle1.putString("vehicle", "SUV");
                        bundle1.putString("travel_type", type);
                        bottomSheet2.setArguments(bundle1);
                        bottomSheet2.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;
                }
                return true;
            }
        });
    }

    private void getCitynavigation(String typeof) {
        Log.i("my_tag", "Welcome");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("my_tag", "Welcome2");
                switch (menuItem.getTitle().toString()) {
                    case "Auto":
                        cityBottomsheet bottomSheet = new cityBottomsheet();
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("pickn", pickupLocTxt.getText().toString());
                        bundle0.putString("dropn", dropLocTxt.getText().toString());

//                        bundle0.putString("ori_lat", String.valueOf(origin.latitude));
//                        bundle0.putString("ori_lng", String.valueOf(origin.longitude));
//                        bundle0.putString("dest_lat", String.valueOf(dest.latitude));
//                        bundle0.putString("dest_lng", String.valueOf(dest.longitude));

                        bundle0.putString("vehicle", "Auto");
                        bundle0.putString("travel_type", type);
                        bottomSheet.setArguments(bundle0);
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;

                    case "Prime":
                        cityBottomsheet bottomSheet1 = new cityBottomsheet();
                        Bundle bundle = new Bundle();
                        bundle.putString("pickn", pickupLocTxt.getText().toString());
                        bundle.putString("dropn", dropLocTxt.getText().toString());

//                        bundle.putString("ori_lat", String.valueOf(origin.latitude));
//                        bundle.putString("ori_lng", String.valueOf(origin.longitude));
//                        bundle.putString("dest_lat", String.valueOf(dest.latitude));
//                        bundle.putString("dest_lng", String.valueOf(dest.longitude));

                        bundle.putString("vehicle", "Prime");
                        bundle.putString("travel_type", type);
                        bottomSheet1.setArguments(bundle);
                        bottomSheet1.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;

                    case "SUV":

                        cityBottomsheet bottomSheet2 = new cityBottomsheet();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("pickn", pickupLocTxt.getText().toString());
                        bundle1.putString("dropn", dropLocTxt.getText().toString());
//
//                        bundle1.putString("ori_lat", String.valueOf(origin.latitude));
//                        bundle1.putString("ori_lng", String.valueOf(origin.longitude));
//                        bundle1.putString("dest_lat", String.valueOf(dest.latitude));
//                        bundle1.putString("dest_lng", String.valueOf(dest.longitude));

                        bundle1.putString("vehicle", "SUV");
                        bundle1.putString("travel_type", type);
                        bottomSheet2.setArguments(bundle1);
                        bottomSheet2.show(getSupportFragmentManager(), "exampleBottomSheet");
                        break;
                }
                return true;
            }
        });
    }


    private void slideDown(View view) {
        myView.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        view.startAnimation(animate);
    }

    public void Searchpickup(View view) {
        Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
        dropP = 0;
        pickP = 1;
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                    .build(HomeScreen.this).putExtra(MESSAGE, "pick");
            Toast.makeText(this, "Intent  " + intent.toString(), Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, REQUEST_SELECT_PICK);


        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void Searchdrop(View view) {
        Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
        dropP = 1;
        pickP = 0;
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_FULLSCREEN)
                    .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                    .build(HomeScreen.this).putExtra(MESSAGE, "pick");
            Toast.makeText(this, "Intent  " + intent.toString(), Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, REQUEST_SELECT_PICK);

        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert data != null;
        Log.i(LOG_TAG, "Place Data: " + data.toString());

        String det = "pick";
        final int REQUEST_SELECT_PLACE = 1000;

        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPlaceSelected(Place place) {

        Log.i(LOG_TAG, "Place Selected: " + place.getName());

        if (pickP == 1) {
            Geocoder geocoder;
            List<Address> addresses;
            String address = "";
            geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
            try {
                LatLng searchlatlang = place.getLatLng();
                addresses = geocoder.getFromLocation(searchlatlang.latitude, searchlatlang.longitude, 1);
                address = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(HomeScreen.this, "Can't get Address", Toast.LENGTH_SHORT).show();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            pickupLocTxt.setText(address);
            addMarker(place);
        } else {
            Geocoder geocoder;
            List<Address> addresses;
            LatLng searchlatlang = place.getLatLng();
            String address = "";
            geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(searchlatlang.latitude, searchlatlang.longitude, 1);
                address = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(HomeScreen.this, "Can't get Address", Toast.LENGTH_SHORT).show();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            dropLocTxt.setText(address);
            addMarker(place);
        }
    }

    public void addMarker(Place place) {

        try {

            if (pickP == 1) {
                if (oriMarker != null) {
                    oriMarker.remove();
                    MarkerPoints.remove(0);
                }

                MarkerOptions markerOptionsOri = new MarkerOptions();
                markerOptionsOri.position(place.getLatLng());
                markerOptionsOri.title("Starting point");
                markerOptionsOri.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                oriMarker = mMap.addMarker(markerOptionsOri);
                mMap.addMarker(markerOptionsOri).setDraggable(true);
                MarkerPoints.add(0, place.getLatLng());
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            } else {
                if (destMarker != null) {
                    destMarker.remove();
                    MarkerPoints.remove(1);
                }
                MarkerOptions markerOptionsDes = new MarkerOptions();
                markerOptionsDes.position(place.getLatLng());
                markerOptionsDes.title("Destination point");
                markerOptionsDes.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                destMarker = mMap.addMarker(markerOptionsDes);
                mMap.addMarker(markerOptionsDes).setDraggable(true);
                MarkerPoints.add(1, place.getLatLng());
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }

            if (MarkerPoints.size() > 1) {
                origin = MarkerPoints.get(0);
                dest = MarkerPoints.get(1);
                Log.i(LOG_TAG, "origin marker: " + origin.toString());
                Log.i(LOG_TAG, "destination marker: " + dest.toString());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.i("Error in  ", e.getMessage());
            Toast.makeText(HomeScreen.this, "Error in Search", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //MEthod  for Bottom Sheet
    @Override
    public void onButtonClicked(String text) {

    }
}
