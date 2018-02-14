package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.CartActivity;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.CooldrinksResponse;
import yummy.mryummy.Splash.Model.DrinksSelectCartResponse;
import yummy.mryummy.Splash.Model.Drinksdatum;
import yummy.mryummy.Splash.Model.Sliderset;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 2/5/2018.
 */

public class CooldrinksAdapter extends RecyclerView.Adapter<CooldrinksAdapter.ViewHolder> {

    private List<Drinksdatum> drinksdata;
    private Context context;
    private String user_id,rest_id,product_id,quantity;

    public CooldrinksAdapter(Context context,List<Drinksdatum> drinksdata) {
        this.drinksdata = drinksdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allrestaurent_slider_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaNeue-Bold.ttf");

        Picasso.with(context).load(drinksdata.get(position).getItemImage()).resize(120, 60)
                .placeholder(R.drawable.app200logo).into(viewHolder.sliderdata_image);

        user_id = Helper.getLocalValue(context,"user_id");
        rest_id = Helper.getLocalValue(context,"rest_ID");
        quantity = "1";

        viewHolder.sliderdata_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drinksaddcartmethod(user_id,rest_id,drinksdata.get(position).getItemId(),quantity);
            }
        });
    }

    private void drinksaddcartmethod(String user_id, String rest_id, String product_id, String quantity) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("restaurant_id", rest_id);
        map.put("product_id", product_id);
        map.put("quantity", quantity);

        ApiUtils.getyummyservice().cooldrinkscart(map)
                .enqueue(new Callback<DrinksSelectCartResponse>() {
                    @Override
                    public void onResponse(Call<DrinksSelectCartResponse> call, Response<DrinksSelectCartResponse> response) {
                        int getStatus = Integer.parseInt(response.body().getStatuscode());
                        try{
                            if(getStatus == 200){
                                DrinksSelectCartResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.drinkCartData != null && result.drinkCartData.size() != 0) {
                                    Toast.makeText(context, "Item saved to cart", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Please Try Again..!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(getStatus == 400){
                                Toast.makeText(context, "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(context, "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DrinksSelectCartResponse> call, Throwable t) {

                    }
                });
    }

// inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView sliderdata_image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            sliderdata_image = (ImageView) itemLayoutView.findViewById(R.id.slider_image);
        }
    }
    // Return the size of your orderslist (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return drinksdata.size();
    }
}

