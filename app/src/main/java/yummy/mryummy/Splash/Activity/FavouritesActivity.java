package yummy.mryummy.Splash.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Adapter.AllRestaurentListAdapter;
import yummy.mryummy.Splash.Adapter.FavouritesAdapter;
import yummy.mryummy.Splash.Adapter.RestaurentSlider;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.FavouriteResponse;
import yummy.mryummy.Splash.Model.Restaurantlist;
import yummy.mryummy.Splash.Model.RestaurantsDatum;
import yummy.mryummy.Splash.Model.RestaurentResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 2/7/2018.
 */

public class FavouritesActivity extends AppCompatActivity {

    String userid;
    FavouritesAdapter favouritesAdapter;
    RecyclerView recyclerView;
    ShimmerRecyclerView shimmerRecycler;
    Context context;
    TextView slide_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites_fragment_layout);

        userid = Helper.getLocalValue(FavouritesActivity.this,"user_id");
        slide_text = (TextView) findViewById(R.id.slide_text);

       /* recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavouritesActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);*/
        shimmerRecycler = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.showShimmerAdapter();
        favouritemethod(userid);
    }

    private void favouritemethod(String userid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", userid);

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();*/

        ApiUtils.getyummyservice().favouritelist(map)
                .enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                        int getStatus = Integer.parseInt(response.body().getResponseStatus().getStatuscode());
                        //   progressDialog.dismiss();
                        try{
                            if(getStatus == 200){
                                FavouriteResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.fItemsData != null && result.fItemsData.size() != 0) {
                                    slide_text.setText("FAVOURITES  " + "("+result.fItemsData.size()+")");
                                    favouritesAdapter = new FavouritesAdapter(FavouritesActivity.this,result.getResponseStatus(),result.getFItemsData());
                                    //  recyclerView.setAdapter(favouritesAdapter);
                                    shimmerRecycler.setAdapter(favouritesAdapter);
                                }
                            }else if(response.body().getResponseStatus().getStatuscode() == "400"){
                                Toast.makeText(FavouritesActivity.this, "something went wrong...!", Toast.LENGTH_SHORT).show();
                            } else{

                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                        t.printStackTrace();
                       /* if (progressDialog.isShowing())
                            progressDialog.dismiss();*/
                    }
                });

    }
}
