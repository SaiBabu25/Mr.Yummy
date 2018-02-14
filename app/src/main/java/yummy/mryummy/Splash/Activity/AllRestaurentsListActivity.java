package yummy.mryummy.Splash.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Adapter.AllRestaurentListAdapter;
import yummy.mryummy.Splash.Adapter.RestaurentSlider;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.Restaurantlist;
import yummy.mryummy.Splash.Model.RestaurantsDatum;
import yummy.mryummy.Splash.Model.RestaurentResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/15/2018.
 */

public class AllRestaurentsListActivity extends AppCompatActivity implements LocationListener {

    private TextView toolbar_location;
    private String latt, longt;
    private TextView image_filter, image_back;
    RecyclerView recyclerView;
    RecyclerView horizontalScrollView1;
    RestaurentSlider restaurentslider;
    ShimmerRecyclerView shimmerRecycler,shimmer_recycler_view_horiz_list;
    AllRestaurentListAdapter allrestaurentsAdapter;
    List<RestaurantsDatum> restaurentdata = new ArrayList<RestaurantsDatum>();
    List<Restaurantlist> restaurentsstatus = new ArrayList<Restaurantlist>();
    public static Context mContext;
    private List<RestaurentResponse> data;
    private AllRestaurentListAdapter adapter;
    private String current_location, latitude, longitude;
    TextView slide_text, restaurents_text;
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allrestaurent_list_layout);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
          return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, this);

        //  mInflater = LayoutInflater.from(this);
        mContext = AllRestaurentsListActivity.this;
        current_location = getIntent().getStringExtra("current_location");
        toolbar_location = (TextView) findViewById(R.id.toolbar_location);
        //  toolbar_location.setText(Helper.getLocalValue(getApplicationContext(), "current_save_location"));
      //  toolbar_location.setText(Helper.getLocalValue(getApplicationContext(),"login_address"));

       /* latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");*/

        latt = Helper.getLocalValue(getApplicationContext(),"location_latitude");
        longt =Helper.getLocalValue(getApplicationContext(),"location_longitude");

       /* latt = Helper.getLocalValue(getApplicationContext(), "latitude_saved");
        longt = Helper.getLocalValue(getApplicationContext(), "longitude_saved"); */

        image_back = (TextView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(myIntent);
            }
        });

       /* recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allrestaurentsAdapter); // set the Adapter to RecyclerView*/

        shimmerRecycler = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_list);
        shimmerRecycler.showShimmerAdapter();

        horizontalScrollView1 = (RecyclerView) findViewById(R.id.horizontalScrollView1);
        horizontalScrollView1.setHasFixedSize(true);
        horizontalScrollView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        horizontalScrollView1.setAdapter(restaurentslider); // set the Adapter to RecyclerView

      /*  shimmer_recycler_view_horiz_list = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_horiz_list);
        shimmerRecycler.showShimmerAdapter();*/

        slide_text = (TextView) findViewById(R.id.slide_text);
        restaurents_text = (TextView)  findViewById(R.id.restaurents_text);

        totalrestaurentslist(latt,longt);
    }

    private void totalrestaurentslist(String latt, String longt) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("category", "Restaurants");
       /* map.put("latitude", latt);
        map.put("longitude", longt);*/
        map.put("latitude", 17.9689008);
        map.put("longitude", 79.5940544);

       /* final ProgressDialog progressDialog = new ProgressDialog(AllRestaurentsListActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();*/

        ApiUtils.getyummyservice().restaurentslist(map)
                .enqueue(new Callback<RestaurentResponse>() {
                    @Override
                    public void onResponse(Call<RestaurentResponse> call, Response<RestaurentResponse> response) {
                         //  progressDialog.dismiss();
                         int getStatus = Integer.parseInt(response.body().getRestaurantlist().getStatuscode());
                        try{
                            if(getStatus == 200){
                                RestaurentResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.restaurantsData != null && result.restaurantsData.size() != 0) {

                                    slide_text.setText("Let's hook you up with a good lunch.");
                                    restaurentslider = new RestaurentSlider(getApplicationContext(),
                                            result.getSliderset());
                                    horizontalScrollView1.setAdapter(restaurentslider);
                                  //  shimmer_recycler_view_horiz_list.setAdapter(restaurentslider);

                                    restaurents_text.setText(result.restaurantsData.size()+ "  RESTAURANTS");
                                    allrestaurentsAdapter = new AllRestaurentListAdapter(getApplicationContext(),
                                            result.getRestaurantsData());
                                   // recyclerView.setAdapter(allrestaurentsAdapter);
                                    shimmerRecycler.setAdapter(allrestaurentsAdapter);

                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(response.body().getRestaurantlist().getStatuscode() == "400"){
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            } else{

                              /*  AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("No Data")
                                        //  .setNegativeButton("Retry", null)
                                        .create()
                                        .show();*/
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                             Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurentResponse> call, Throwable t) {
                        t.printStackTrace();
                       /* if (progressDialog.isShowing())
                            progressDialog.dismiss();*/
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {

        Helper.storeLocally("location_latitude", String.valueOf(location.getLatitude()),getApplicationContext());
        Helper.storeLocally("location_longitude", String.valueOf(location.getLongitude()),getApplicationContext());

      /*----------to get City-Name from coordinates ------------- */
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            toolbar_location.setText(addresses.get(0).getLocality()+","+addresses.get(0).getAddressLine(0));
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
        Toast.makeText(AllRestaurentsListActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}

