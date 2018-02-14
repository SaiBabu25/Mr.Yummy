package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.CartActivity;
import yummy.mryummy.Splash.Activity.CartActivity2;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.ItemsDatum;
import yummy.mryummy.Splash.Model.ResponseStatus;
import yummy.mryummy.Splash.Model.Searchitem;

/**
 * Created by acer on 1/21/2018.
 */

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.ViewHolder> {


    private List<Searchitem> itemsDatas;
    private ResponseStatus responsestatues;
    private Context context;
    private int counter = 0;
    private int prise = 0;
    private boolean snackinitialise=false;
    Snackbar snackbar = null;
    private String cgst="0",sgst="0",d_charges="0",image="null";
    private boolean like_status;

   /* public AllRestaurentItemsListData(Context context, List<ItemsDatum> itemsDatas,
                                    ResponseStatus responsestatues) {
        this.itemsDatas = itemsDatas;
        this.responsestatues = responsestatues;
        this.context = context;
    }*/

    public SearchItemsAdapter(Context context, List<Searchitem> itemsDatas,
                              String sgst, String cgst, String restcharge,String image) {
        this.itemsDatas = itemsDatas;
        this.responsestatues = responsestatues;
        this.context = context;
        this.image=image;
        if (!cgst.equals("null")&&!cgst.equals("")){
            this.cgst= cgst;
        }
        if (!sgst.equals("null")&&!sgst.equals("")){
            this.sgst= sgst;
        }
        if (restcharge!=null) {
            if (!restcharge.equals("null") && !restcharge.equals("")) {
                this.d_charges = restcharge;
            }
        }

        Log.e("tax","cgst "+cgst+"sgst "+sgst +" d_charges "+d_charges);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurent_item_adapter_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/HelveticaNeue-Bold.ttf");

        viewHolder.item_name.setText(itemsDatas.get(position).getItemName());
        viewHolder.item_name.setTypeface(font);

        viewHolder.item_category.setText(itemsDatas.get(position).getItemSize());
        viewHolder.item_category.setTypeface(font);

      /*  viewHolder.item_category.setText(itemsDatas.get(position).getItemCategory());
        viewHolder.item_category.setTypeface(font);

        viewHolder.item_price.setText(" \u20B9 "+itemsDatas.get(position).getPrice());
        viewHolder.item_price.setTypeface(font);*/

        Picasso.with(context).load(itemsDatas.get(position).getImage()).resize(120, 60)
                .placeholder(R.drawable.app200).into(viewHolder.item_image);


       /* if(itemsDatas.get(position).getItemType().equals("Non-Veg")){
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
            viewHolder.img_icon_veg.setVisibility(View.GONE);
        }else{
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        }

        if(itemsDatas.get(position).getItemType().equals("Veg")){
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
            viewHolder.img_icon_veg.setVisibility(View.GONE);
        }*/

        if(counter == 0){
            viewHolder.item_add.setVisibility(View.VISIBLE);
            viewHolder.button_layout.setVisibility(View.GONE);
        }else{
            viewHolder.item_add.setVisibility(View.GONE);
            viewHolder.button_layout.setVisibility(View.VISIBLE);
        }


        viewHolder.item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.item_add.setVisibility(View.GONE);
                viewHolder.button_layout.setVisibility(View.VISIBLE);
                viewHolder.display.setText("1");
                counter=counter+1;
                try {
                    prise = prise + Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ", ""));
                }catch(NumberFormatException nfe){

                }
                HomeActivity.selected_itemdata5.add(itemsDatas.get(position));
                snackbar(v);
            }
        });


        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds 1 to the counter
                counter=counter+1;
                try{
                prise=prise+Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ",""));
                }catch(NumberFormatException nfe){

                }viewHolder.display.setText(String.valueOf(Integer.parseInt(viewHolder.display.getText().toString())+1));
                HomeActivity.selected_itemdata5.add(itemsDatas.get(position));
                snackbar(v);


            }
        });

        viewHolder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Subtract 1 from counter
                counter = counter - 1;
                try{
                    prise=prise-Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ",""));
                }catch(NumberFormatException nfe){

                }
                snackbar(v);
                if (Integer.parseInt(viewHolder.display.getText().toString()) <= 1) {
                    viewHolder.item_add.setVisibility(View.VISIBLE);
                    viewHolder.button_layout.setVisibility(View.GONE);
                }
                HomeActivity.selected_itemdata5.remove(itemsDatas.get(position));
                viewHolder.display.setText(String.valueOf(Integer.parseInt(viewHolder.display.getText().toString())-1));

            }
        });


    }


    private void snackbar(View v) {
        if (!snackinitialise) {
            snackinitialise=true;
            snackbar = Snackbar.make(v, counter + " All Items added to cart Section user added" +
                    "All Items added to cart Section user added |" + " \u20B9 " + prise, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(context.getResources().getColor(R.color.white));

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            final Snackbar finalSnackbar = snackbar;
            snackbar.setAction("VIEW CART", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalSnackbar.dismiss();
                    Intent intent=new Intent(context,CartActivity2.class);
                    intent.putExtra("items",counter);
                    intent.putExtra("cost",prise);
                    intent.putExtra("cgst",Integer.parseInt(cgst));
                    intent.putExtra("sgst",Integer.parseInt(sgst));
                    intent.putExtra("dcharge",Integer.parseInt(d_charges));
                    intent.putExtra("rest_pic",image);
                    // intent.putExtra("s_item_data", (Parcelable) selected_itemdata);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                   /* ((Activity)context).finish();*/
                }
            });
            snackbar.show();
        }else {
            snackbar.setText(counter + " items |" + " \u20B9 " + prise);
        }
        if (prise==0){
            snackbar.dismiss();
        }else {
            snackbar.show();
        }
    }


    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView restaurent_card;
        public TextView item_name,item_category,item_price;
        public ImageView item_image,img_icon_veg,img_icon_nonveg,img_lov_icon,img_lov_icon_select;
        public Button item_add;
        public LinearLayout button_layout;
        public int counter;
        public TextView add,sub,display;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            //  restaurent_card = (CardView) itemLayoutView.findViewById(R.id.restaurent_card);
            item_image = (ImageView) itemLayoutView.findViewById(R.id.item_image);
            img_icon_nonveg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_nonveg);
            img_icon_veg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_veg);
            // img_lov_icon = (ImageView) itemLayoutView.findViewById(R.id.img_lov_icon);
            //   img_lov_icon_select = (ImageView) itemLayoutView.findViewById(R.id.img_lov_icon_select);
            item_name = (TextView) itemLayoutView.findViewById(R.id.item_name);
            item_category = (TextView) itemLayoutView.findViewById(R.id.item_category);
            item_price = (TextView) itemLayoutView.findViewById(R.id.item_price);
            item_add = (Button)  itemLayoutView.findViewById(R.id.item_add);
            button_layout = (LinearLayout)  itemLayoutView.findViewById(R.id.button_layout);
            add = (TextView) itemLayoutView.findViewById(R.id.bAdd);
            sub = (TextView) itemLayoutView.findViewById(R.id.bSub);
            display = (TextView) itemLayoutView.findViewById(R.id.tvDisplay);
        }
    }
    // Return the size of your orderslist (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsDatas.size();
    }
}