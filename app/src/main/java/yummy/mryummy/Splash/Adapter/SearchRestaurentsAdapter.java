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

import com.squareup.picasso.Picasso;

import java.util.List;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.SingleRestaurentDetailActivity;
import yummy.mryummy.Splash.Model.Restaurantlist;
import yummy.mryummy.Splash.Model.RestaurantsDatum;
import yummy.mryummy.Splash.Model.SearchRestaurant;

/**
 * Created by acer on 1/21/2018.
 */

public class SearchRestaurentsAdapter extends RecyclerView.Adapter<SearchRestaurentsAdapter.ViewHolder> {


    private List<SearchRestaurant> restaurentdata;
    private Context context;

    public SearchRestaurentsAdapter(Context context,List<SearchRestaurant> restaurentdata) {
        this.restaurentdata = restaurentdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurent_lists_adapter_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaNeue-Bold.ttf");

        viewHolder.restaurent_name.setText(restaurentdata.get(position).getRestaurantName());
        viewHolder.restaurent_name.setTypeface(font);

      /*  viewHolder.restaurent_category.setText(restaurentdata.get(position).getCategories());
        viewHolder.restaurent_category.setTypeface(font);
*/
        viewHolder.retaurent_review.setText(restaurentdata.get(position).getReview());
        viewHolder.retaurent_review.setTypeface(font);

        viewHolder.retaurent_distance.setText(restaurentdata.get(position).getDistance()+" KMS");
        viewHolder.retaurent_distance.setTypeface(font);

        viewHolder.retaurent_charge.setText(" \u20B9 "+restaurentdata.get(position).getDeliveryCharges()+" /-");
        viewHolder.retaurent_charge.setTypeface(font);

        Picasso.with(context).load(restaurentdata.get(position).getImage()).resize(120, 60)
                .placeholder(R.drawable.app200).into(viewHolder.restaurent_image);


        viewHolder.restaurent_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("rest_id", restaurentdata.get(position).getId());
                bundle.putString("rest_name", restaurentdata.get(position).getRestaurantName());
                bundle.putString("rest_review", restaurentdata.get(position).getReview());
                bundle.putString("rest_distance", restaurentdata.get(position).getDistance());
                bundle.putString("rest_category", restaurentdata.get(position).getCategories());
                bundle.putString("rest_charge", restaurentdata.get(position).getDeliveryCharges());
                bundle.putString("rest_image", restaurentdata.get(position).getImage());
                bundle.putString("cgst", String.valueOf(restaurentdata.get(position).getCgst()));
                bundle.putString("sgst", String.valueOf(restaurentdata.get(position).getSgst()));
                bundle.putString("rest_pic", String.valueOf(restaurentdata.get(position).getImage()));
                Intent i = new Intent(context, SingleRestaurentDetailActivity.class);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        viewHolder.restaurent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("rest_id", restaurentdata.get(position).getId());
                bundle.putString("rest_name", restaurentdata.get(position).getRestaurantName());
                bundle.putString("rest_review", restaurentdata.get(position).getReview());
                bundle.putString("rest_distance", restaurentdata.get(position).getDistance());
                bundle.putString("rest_category", restaurentdata.get(position).getCategories());
                bundle.putString("rest_charge", restaurentdata.get(position).getDeliveryCharges());
                bundle.putString("rest_image", restaurentdata.get(position).getImage());
                bundle.putString("cgst", String.valueOf(restaurentdata.get(position).getCgst()));
                bundle.putString("sgst", String.valueOf(restaurentdata.get(position).getSgst()));
                bundle.putString("rest_pic", String.valueOf(restaurentdata.get(position).getImage()));
                Intent i = new Intent(context, SingleRestaurentDetailActivity.class);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView restaurent_card;
        public TextView restaurent_name,restaurent_category,retaurent_review,retaurent_distance,retaurent_charge;
        public ImageView restaurent_image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            restaurent_card = (CardView) itemLayoutView.findViewById(R.id.rest_card);
            restaurent_image = (ImageView) itemLayoutView.findViewById(R.id.restaurent_image);
            restaurent_name = (TextView) itemLayoutView.findViewById(R.id.restaurent_name);
           // restaurent_category = (TextView) itemLayoutView.findViewById(R.id.restaurent_category);
            retaurent_review = (TextView) itemLayoutView.findViewById(R.id.retaurent_review);
            retaurent_distance = (TextView) itemLayoutView.findViewById(R.id.retaurent_distance);
            retaurent_charge = (TextView) itemLayoutView.findViewById(R.id.retaurent_charge);
        }
    }
    // Return the size of your orderslist (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restaurentdata.size();
    }
}

