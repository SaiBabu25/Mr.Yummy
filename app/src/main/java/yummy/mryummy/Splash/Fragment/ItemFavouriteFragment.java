package yummy.mryummy.Splash.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.AllRestaurentsListActivity;
import yummy.mryummy.Splash.Adapter.FavouritesAdapter;
import yummy.mryummy.Splash.Adapter.SearchItemsAdapter;
import yummy.mryummy.Splash.Adapter.SearchRestaurentsAdapter;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.FavouriteResponse;
import yummy.mryummy.Splash.Model.SearchResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/30/2018.
 */

public class ItemFavouriteFragment extends Fragment {

    String userid;
    FavouritesAdapter favouritesAdapter;
    RecyclerView recyclerView;
    ShimmerRecyclerView shimmerRecycler;
    Context context;
    TextView slide_text;

    public static ItemFavouriteFragment newInstance() {
        ItemFavouriteFragment fragment = new ItemFavouriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites_fragment_layout, container, false);

        userid = Helper.getLocalValue(getActivity(),"user_id");
        slide_text = (TextView) view.findViewById(R.id.slide_text);

       /* recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
*/
        shimmerRecycler = (ShimmerRecyclerView)view.findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.showShimmerAdapter();
        favouritemethod(userid);
        return view;
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
                                    favouritesAdapter = new FavouritesAdapter(getActivity(),result.getResponseStatus(),result.getFItemsData());
                                //  recyclerView.setAdapter(favouritesAdapter);
                                   shimmerRecycler.setAdapter(favouritesAdapter);
                                }
                            }else if(response.body().getResponseStatus().getStatuscode() == "400"){
                                Toast.makeText(getActivity(), "something went wrong...!", Toast.LENGTH_SHORT).show();
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

