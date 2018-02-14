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
import yummy.mryummy.Splash.Model.FItemsDatum;
import yummy.mryummy.Splash.Model.ResponseStatus;
import yummy.mryummy.Splash.Model.Restaurantlist;
import yummy.mryummy.Splash.Model.RestaurantsDatum;

/**
 * Created by acer on 1/30/2018.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {


    private ResponseStatus restaurentsstatus;
    private List<FItemsDatum> favouritedata;
    private Context context;

    public FavouritesAdapter(Context context, ResponseStatus restaurentsstatus, List<FItemsDatum> favouritedata) {
        this.favouritedata = favouritedata;
        this.restaurentsstatus = restaurentsstatus;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourites_lists_adapter_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaNeue-Bold.ttf");

        viewHolder.item_name.setText(favouritedata.get(position).getItemName());
        viewHolder.item_name.setTypeface(font);

        viewHolder.item_size.setText(favouritedata.get(position).getItemSize());
        viewHolder.item_size.setTypeface(font);

        viewHolder.item_price.setText(" \u20B9 " +favouritedata.get(position).getPrice());
        viewHolder.item_price.setTypeface(font);

        Picasso.with(context).load(favouritedata.get(position).getItemImage()).resize(120, 60)
                .placeholder(R.drawable.app200).into(viewHolder.item_image);

        if(favouritedata.get(position).getItemType().equals("Non-Veg")){
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
           viewHolder.img_icon_veg.setVisibility(View.GONE);
        }else{
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        }

        if(favouritedata.get(position).getItemType().equals("Veg")){
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
            viewHolder.img_icon_veg.setVisibility(View.GONE);
        }

        viewHolder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("item_id", favouritedata.get(position).getItemId());
                bundle.putString("restaurant_id", favouritedata.get(position).getRestaurantId());
                bundle.putString("item_category", favouritedata.get(position).getItemCategory());
                bundle.putString("item_name", favouritedata.get(position).getItemName());
                bundle.putString("item_type", favouritedata.get(position).getItemType());
                bundle.putString("item_size", favouritedata.get(position).getItemSize());
                bundle.putString("price", favouritedata.get(position).getPrice());
                bundle.putString("item_image", String.valueOf(favouritedata.get(position).getItemImage()));
                bundle.putString("item_status", String.valueOf(favouritedata.get(position).getItemStatus()));
                bundle.putString("created_on", String.valueOf(favouritedata.get(position).getCreatedOn()));
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
                bundle.putString("item_id", favouritedata.get(position).getItemId());
                bundle.putString("restaurant_id", favouritedata.get(position).getRestaurantId());
                bundle.putString("item_category", favouritedata.get(position).getItemCategory());
                bundle.putString("item_name", favouritedata.get(position).getItemName());
                bundle.putString("item_type", favouritedata.get(position).getItemType());
                bundle.putString("item_size", favouritedata.get(position).getItemSize());
                bundle.putString("price", favouritedata.get(position).getPrice());
                bundle.putString("item_image", String.valueOf(favouritedata.get(position).getItemImage()));
                bundle.putString("item_status", String.valueOf(favouritedata.get(position).getItemStatus()));
                bundle.putString("created_on", String.valueOf(favouritedata.get(position).getCreatedOn()));
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
        public TextView item_name,item_type,item_size,item_price;
        public ImageView item_image,img_icon_veg,img_icon_nonveg;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            restaurent_card = (CardView) itemLayoutView.findViewById(R.id.rest_card);
            item_image = (ImageView) itemLayoutView.findViewById(R.id.item_image);
            item_name = (TextView) itemLayoutView.findViewById(R.id.item_name);
            item_size = (TextView) itemLayoutView.findViewById(R.id.item_size);
            item_price = (TextView) itemLayoutView.findViewById(R.id.item_price);
            img_icon_nonveg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_nonveg);
            img_icon_veg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_veg);
        }
    }
    // Return the size of your orderslist (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return favouritedata.size();
    }
}

