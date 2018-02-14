package yummy.mryummy.Splash.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.ItemsDatum;
import yummy.mryummy.Splash.Model.Searchitem;
import yummy.mryummy.databinding.ActivityCartBinding;
import yummy.mryummy.databinding.CustomRestaurentItemBinding;

/**
 * Created by Renown2 on 1/22/2018.
 */

public class CartActivity2 extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    int items=0,d_charges=0,cgst=0,sgst=0;
    String image;
    double gst=0.0,cost=0;
    ActivityCartBinding binding;
    CustomRestaurentItemBinding itemAdapterLayoutBinding;
    ArrayList<String> itemids=new ArrayList<>();
    HashMap<String,Integer> items_count=new HashMap<>();
    List<Searchitem> uniquedata=new ArrayList<>();
    List<Searchitem> selected_items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(CartActivity2.this, R.layout.activity_cart);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout1);
        items=getIntent().getIntExtra("items",0);
        cost=getIntent().getIntExtra("cost",0);
        cgst=getIntent().getIntExtra("cgst",0);
        sgst=getIntent().getIntExtra("sgst",0);
        d_charges=getIntent().getIntExtra("dcharge",0);
        image=getIntent().getStringExtra("rest_pic");
        binding.sItems.removeAllViews();
        itemids=new ArrayList<>();
        uniquedata=new ArrayList<>();
        ArrayList<String> ids=new ArrayList<>();
        selected_items.addAll(HomeActivity.selected_itemdata5);
        for (int i=0;i<selected_items.size();i++) {
            itemids.add(selected_items.get(i).getItemId());
           /* if (uniquedata.size()==0){
                uniquedata.add(selected_items.get(i));
                ids.add(selected_items.get(i).getItemId());
            }else {*/
            if (!ids.contains(selected_items.get(i).getItemId())) {
                ids.add(selected_items.get(i).getItemId());
                uniquedata.add(selected_items.get(i));
            }
           /* }*/
        }
        Set<String> unique = new HashSet<String>(itemids);
        items_count=new HashMap<>();
        for (String key : unique) {
            Log.e("duplicate",key + ": " + Collections.frequency(itemids, key));
            items_count.put(key,Collections.frequency(itemids, key));
        }
        for (int i=0;i<uniquedata.size();i++) {

            itemAdapterLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),R.layout.custom_restaurent_item,null,false);
            itemAdapterLayoutBinding.itemName.setText(String.valueOf(uniquedata.get(i).getItemName()));
          //  itemAdapterLayoutBinding.item_category.setText(String.valueOf(uniquedata.get(i).getItemSize()));
            itemAdapterLayoutBinding.itemPrice.setText(String.valueOf("0"));
            itemAdapterLayoutBinding.tvDisplay.setText(String.valueOf(items_count.get(uniquedata.get(i).getItemId())));
            binding.sItems.addView(itemAdapterLayoutBinding.getRoot());

        }
        binding.title.setText("Cart");
        binding.titleText.setText(items+" items, To Pay :  \u20B9 "+(cost+d_charges));
        binding.itemtotal.setText("  \u20B9 "+cost);
        gst=((double)((double)((cgst+sgst)/cost))*100);
        binding.gst.setText("  \u20B9 "+String.valueOf(gst));
        binding.deliveryCharges.setText("  \u20B9 "+d_charges);
        binding.toPay.setText("To Pay "+(cost+gst+d_charges));
        Picasso.with(CartActivity2.this).load(image).resize(120, 60)
                .placeholder(R.drawable.app200).into(binding.image);

    }}
