package yummy.mryummy.Splash.Registration;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import com.google.android.gms.location.LocationListener;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.AddressRespponse;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

import static yummy.mryummy.R.id.map;

/**
 * Created by acer on 11/25/2017.
 */

public class AddressSetActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    LocationManager locationManager ;
    boolean GpsStatus ;

    TextView address_tile, skip, text_save, home, work, others;
    TextInputLayout input_layout_address, input_layout_house;
    EditText input_address, input_house, input_landmark;
    Button save;
    private String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_layout);

        user_id = Helper.getLocalValue(getApplicationContext(), "user_id");

        address_tile = (TextView) findViewById(R.id.address_tile);
         skip = (TextView) findViewById(R.id.skip);
        address_tile.setText(Html.fromHtml("<u><b><font color='#FA6C04'>SAVE DELIVERY LOCATION</font></b></u>"));
        input_layout_address = (TextInputLayout) findViewById(R.id.input_layout_address);
        input_layout_house = (TextInputLayout) findViewById(R.id.input_layout_house);
        input_address = (EditText) findViewById(R.id.input_address);
        input_house = (EditText) findViewById(R.id.input_house);
        input_landmark = (EditText)  findViewById(R.id.input_landmark);
       // text_save = (TextView) findViewById(R.id.text_save);
        home = (TextView) findViewById(R.id.home);
      //  work = (TextView) findViewById(R.id.work);
      //  others = (TextView) findViewById(R.id.others);
        save = (Button) findViewById(R.id.save);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        CheckGpsStatus() ;
        if(GpsStatus == true)
        {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Location Services Is Enabled", Snackbar.LENGTH_LONG)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                    .show();
        }else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Location Services Is Disabled", Snackbar.LENGTH_LONG)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                    .show();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (input_address.getText() != null || input_house.getText() != null
                                || input_landmark.getText() != null) {
                            String location = input_address.getText().toString().trim();
                            String housenbr = input_house.getText().toString().trim();
                            String landmark = input_landmark.getText().toString().trim();
                            //permissionCheck();
                            if (location.length() != 0 || housenbr.length() != 0  || landmark.length()  != 0 ) {
                                upload(location, housenbr, landmark);
                            }

                            if (location.length() == 0) {
                                input_address.setError("The location field is required");
                            }

                            if (input_address.getText() == null) {
                                input_address.setError("The location field is required");
                            }

                        }
                    } else {
                        //  Helper.showSnackBar(LoginActivity.this.getView(), "No internet connection");
                        //  Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAction("Try Again", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                                .show();
                    }
                } catch (Exception e) {
                    Log.d("Tag","error: "+e.getMessage());
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void upload(String location, String housenbr, String landmark) {
        //addresssave

      //  user_id=16&location=wgl&flat=82&landmark=Thetatre

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("location", location);
        map.put("flat", housenbr);
        map.put("landmark", landmark);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("saving address...");
        progressDialog.show();


        ApiUtils.getyummyservice().saveaddress(map).enqueue(new Callback<AddressRespponse>() {
            @Override
            public void onResponse(@NonNull Call<AddressRespponse> call, @NonNull Response<AddressRespponse> response) {
                progressDialog.dismiss();

                int getStatus = Integer.parseInt(response.body().getStatuscode());
                try {
                    Log.d("Tag", "response: " + response.body());
                    if(getStatus == 200) {
                        Helper.storeLocally("location_store", response.body().getAddress().getLocation(), getApplicationContext());
                        Helper.storeLocally("flat_store", response.body().getAddress().getFlat(), getApplicationContext());
                        Helper.storeLocally("landmark_store", response.body().getAddress().getLandmark(), getApplicationContext());
                       /* Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);*/

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Invalid Credentials", Snackbar.LENGTH_LONG)
                                .setAction("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                                .show();
                        //  Helper.showSnackBar(LoginFragment.this.getView(),"Invalid Credentials");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AddressRespponse> call, Throwable t) {
                t.printStackTrace();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // progressDialog.dismiss();
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Please Try Again...!", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                        .show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
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

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }



           // TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();
          //  LatLng latLng = new LatLng(latitude, longitude);
          /*  googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(21));*/
          //  locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
       // LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        //get the location name from latitude and longitude
        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String result = addresses.get(0).getSubLocality() + ":";
            result += addresses.get(0).getAddressLine(0);
               /* LatLng latLngg = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(result));*/
            //move map camera
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));


            try {
                input_address.setText(result);
            } catch (Exception e) {

            }
            final String finalResult = result;
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("current_location", finalResult);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude",longitude);
                    Toast.makeText(getApplicationContext(),"latitude: "+latitude,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"longitude: "+longitude,Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddressSetActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


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
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
