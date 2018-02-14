package yummy.mryummy.Splash.Fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Adapter.SearchItemsAdapter;
import yummy.mryummy.Splash.Adapter.SearchRestaurentsAdapter;
import yummy.mryummy.Splash.Model.SearchResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;
import yummy.mryummy.databinding.SearchLayoutFragmentBinding;

/**
 * Created by acer on 1/13/2018.
 */

public class ItemSearchFragment extends Fragment {
    SearchLayoutFragmentBinding binding;
    String search_text;
    SearchRestaurentsAdapter allrestaurentsAdapter;
    SearchItemsAdapter allitemsAdapter;
    String restcharge,sgst="0",cgst="0",image="0";


    public static ItemSearchFragment newInstance() {
        ItemSearchFragment fragment = new ItemSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.search_layout_fragment,null,false);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(),1);
        binding.recyclerView.setLayoutManager(gridLayoutManager3);

        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getActivity(),2);
        binding.recyclerView1.setLayoutManager(gridLayoutManager4);

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0){
                    search_text= String.valueOf(charSequence);
                    searchmethod(String.valueOf(charSequence));
                }else {
                    searchmethod("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return binding.getRoot();
    }
    private void searchmethod(String searchtext) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("searchstring", searchtext);
        map.put("latitude", 17.9689008);
        map.put("longitude", 79.5940544);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
       // searchstring=dine&latitude=17.9689008&longitude=79.5940544

        ApiUtils.getyummyservice().searchlist(map)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        Log.e("request",call.request().toString());
                        Log.e("response",response.body().getMessage());
                        progressDialog.dismiss();
                        int getStatus = Integer.parseInt(response.body().getStatuscode());
                        try{
                            if(getStatus == 200){
                                SearchResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.searchRestaurants != null && result.searchRestaurants.size() != 0) {
                                    allrestaurentsAdapter = new SearchRestaurentsAdapter(getActivity(),
                                            result.getSearchRestaurants());
                                    binding.recyclerView.setAdapter(allrestaurentsAdapter);

                                    allitemsAdapter = new SearchItemsAdapter(getActivity(),
                                            result.getSearchitems(),sgst,cgst,restcharge,image);
                                    binding.recyclerView1.setAdapter(allitemsAdapter);

                                }else{
                                 //   Toast.makeText(getActivity(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(response.body().getStatuscode() == "400"){
                                Toast.makeText(getActivity(), "something went wrong...!", Toast.LENGTH_SHORT).show();
                            } else{

                              /*  AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("No Data")
                                        //  .setNegativeButton("Retry", null)
                                        .create()
                                        .show();*/
                                Toast.makeText(getActivity(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

}

