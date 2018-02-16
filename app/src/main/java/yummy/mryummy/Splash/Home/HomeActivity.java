package yummy.mryummy.Splash.Home;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Fragment.FrequentlyAskedFragment;
import yummy.mryummy.Splash.Fragment.ItemFavouriteFragment;
import yummy.mryummy.Splash.Fragment.ItemOneFragment;
import yummy.mryummy.Splash.Fragment.ItemSearchFragment;
import yummy.mryummy.Splash.Fragment.MyAccountFragment;
import yummy.mryummy.Splash.Fragment.NotificationFragment;
import yummy.mryummy.Splash.Fragment.PrivacyPolicyFragment;
import yummy.mryummy.Splash.Fragment.TermsFragment;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.Searchitem;
import yummy.mryummy.Splash.Registration.AddressSetActivity;
import yummy.mryummy.Splash.Registration.LoginActivity;

/**
 * Created by acer on 11/25/2017.
 */

public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,LocationListener {

    private static String TAG = HomeActivity.class.getSimpleName();
    public static List<Searchitem> selected_itemdata5 = new ArrayList<>();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private String current_location,latitude,longitude,login_store_location;
    private String loca1,loca2,loca3;
    TextView toolbar_location;
    private static final String TAG1 = "Debug";
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        flag = displayGpsStatus();
        if (flag) {
            Log.v(TAG, "onClick");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

        toolbar_location = (TextView) findViewById(R.id.toolbar_location);
        current_location = getIntent().getStringExtra("current_location");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");

        Helper.storeLocally("current_save_location",current_location,HomeActivity.this);
        Helper.storeLocally("latitude_saved",latitude,HomeActivity.this);
        Helper.storeLocally("longitude_saved",longitude,HomeActivity.this);

        loca1 = Helper.getLocalValue(getApplicationContext(), "location_store");
        loca2 = Helper.getLocalValue(getApplicationContext(), "flat_store");
        loca3 = Helper.getLocalValue(getApplicationContext(), "landmark_store");
        login_store_location = Helper.getLocalValue(getApplicationContext(),"login_address");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = ItemOneFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = ItemSearchFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = ItemFavouriteFragment.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = ItemOneFragment.newInstance();
                                break;
                            case R.id.action_item5:
                                selectedFragment = MyAccountFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container_body, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, ItemOneFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
           *//* Intent intent  = new Intent(getApplicationContext(), FiltersActivity.class);
            startActivity(intent);*//*
            return true;
        }*/

        if(id == R.id.action_search){
            Intent intent  = new Intent(getApplicationContext(), AddressSetActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = /*getString(R.string.app_name)*/ "";
        switch (position) {
            case 0:
                fragment = new ItemOneFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new NotificationFragment();
                title = getString(R.string.title_notifications);
                break;
            case 2:
                fragment = new TermsFragment();
                title = getString(R.string.title_terms);
                break;
            case 3:
                fragment = new FrequentlyAskedFragment();
                title = getString(R.string.title_faq);
                break;
            case 4:
                fragment = new PrivacyPolicyFragment();
                title = getString(R.string.title_privacy);
                break;
            case 5:
                title = getString(R.string.title_logout);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper.storeLocally("user_id","",HomeActivity.this);
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
          //  getSupportActionBar().setTitle(title);

          /*  if(Helper.getLocalValue(getApplicationContext(), "login_store_location").equals("null")&&
                    Helper.getLocalValue(getApplicationContext(), "login_store_location").equals("") &&
                    Helper.getLocalValue(getApplicationContext(),"login_address").equals("")){*/
            if(Helper.getLocalValue(getApplicationContext(),"login_address").equals(Helper.getLocalValue(getApplicationContext(),"login_address"))){
             //   getSupportActionBar().setTitle( Helper.getLocalValue(getApplicationContext(),"login_address"));
              //  getSupportActionBar().setTitle("KHAMMAM1111111111");
             }else {
                // getSupportActionBar().setTitle(current_location);
              //  getSupportActionBar().setTitle(Helper.getLocalValue(getApplicationContext(), "current_save_location"));
              //  getSupportActionBar().setTitle("KHAMMAM");
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onLocationChanged(Location location) {

      /*----------to get City-Name from coordinates ------------- */
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            getSupportActionBar().setTitle(addresses.get(0).getLocality()+","+addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}