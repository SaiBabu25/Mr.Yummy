package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.AllBakeriesListActivity;
import yummy.mryummy.Splash.Activity.SingleRestaurentDetailActivity;
import yummy.mryummy.Splash.Home.OrderSuccessActivity;
import yummy.mryummy.Splash.Model.Restaurantlist;
import yummy.mryummy.Splash.Model.RestaurantsDatum;
import yummy.mryummy.Splash.Model.Sliderset;

/**
 * Created by acer on 2/5/2018.
 */

public class RestaurentSlider extends RecyclerView.Adapter<RestaurentSlider.ViewHolder> {

    private List<Sliderset> sliderdata;
    private Context context;

    public RestaurentSlider(Context context,List<Sliderset> sliderdata) {
        this.sliderdata = sliderdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allrestaurent_slider_new_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaNeue-Bold.ttf");

        Picasso.with(context).load(sliderdata.get(position).getImage()).resize(120, 60)
                .placeholder(R.drawable.app200).into(viewHolder.sliderdata_image);

       /* viewHolder.sliderdata_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              *//*  if (position == 0) {
                    Intent myIntent = new Intent(context, OrderSuccessActivity.class);
                    context.startActivity(myIntent);
                    Toast.makeText(context, "first pic clicked", Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    Intent myIntent = new Intent(context, OrderSuccessActivity.class);
                    context.startActivity(myIntent);
                    Toast.makeText(context, "Two  clicked", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Intent myIntent = new Intent(context, OrderSuccessActivity.class);
                    context.startActivity(myIntent);
                    Toast.makeText(context, "Three  clicked", Toast.LENGTH_SHORT).show();
                }
                if (position == 3) {
                    Intent myIntent = new Intent(context, OrderSuccessActivity.class);
                    context.startActivity(myIntent);
                    Toast.makeText(context, "Four  clicked", Toast.LENGTH_SHORT).show();
                }*//*
            }
        });*/

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
        return sliderdata.size();
    }
}


