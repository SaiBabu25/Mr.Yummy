package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.graphics.Typeface;
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

import java.util.List;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.SingleRestaurentDetailActivity;
import yummy.mryummy.Splash.Model.ItemsDatum;
import yummy.mryummy.Splash.Model.ResponseStatus;

/**
 * Created by Renown2 on 1/21/2018.
 */

public class AllRestaurentItemsListData4 extends RecyclerView.Adapter<AllRestaurentItemsListData4.ViewHolder> {


    private List<ItemsDatum> itemsDatas;
    private ResponseStatus responsestatues;
    private Context context;


    private String cgst = "0", sgst = "0", d_charges = "0", image = "null";
    private boolean like_status;

    public AllRestaurentItemsListData4(Context context, List<ItemsDatum> itemsDatas,
                                       ResponseStatus responsestatues/*, String sgst, String cgst, String restcharge*/) {
        this.itemsDatas = itemsDatas;
        this.responsestatues = responsestatues;
        this.context = context;
    }

    public AllRestaurentItemsListData4(Context context, List<ItemsDatum> itemsDatas,
                                       ResponseStatus responsestatues, String sgst, String cgst, String restcharge, String image) {
        this.itemsDatas = itemsDatas;
        this.responsestatues = responsestatues;
        this.context = context;
        this.image = image;
        if (!cgst.equals("null") && !cgst.equals("")) {
            this.cgst = cgst;
        }
        if (!sgst.equals("null") && !sgst.equals("")) {
            this.sgst = sgst;
        }
        if (!d_charges.equals("null") && !d_charges.equals("")) {
            this.d_charges = restcharge;
        }

        Log.e("tax", "cgst " + cgst + "sgst " + sgst + " d_charges " + d_charges);

    }

    @Override
    public AllRestaurentItemsListData4.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurent_item_adapter_layout4, null);
        // create ViewHolder
        AllRestaurentItemsListData4.ViewHolder viewHolder = new AllRestaurentItemsListData4.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AllRestaurentItemsListData4.ViewHolder viewHolder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue-Bold.ttf");

        viewHolder.item_name.setText(itemsDatas.get(position).getItemName());
        viewHolder.item_name.setTypeface(font);

        viewHolder.item_category.setText(itemsDatas.get(position).getItemSize());
        viewHolder.item_category.setTypeface(font);

        viewHolder.item_price.setText(" \u20B9 " + itemsDatas.get(position).getPrice());
        viewHolder.item_price.setTypeface(font);

        Log.e("cat", itemsDatas.get(position).getItemType());
        Log.e("image", itemsDatas.get(position).getItemImage());


        if (itemsDatas.get(position).getItemType().equals("Non-Veg")) {
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
            viewHolder.img_icon_veg.setVisibility(View.GONE);
        } else {
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        }

        if (itemsDatas.get(position).getItemType().equals("Veg")) {
            viewHolder.img_icon_nonveg.setVisibility(View.GONE);
            viewHolder.img_icon_veg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_icon_nonveg.setVisibility(View.VISIBLE);
            viewHolder.img_icon_veg.setVisibility(View.GONE);
        }
/*
        viewHolder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.item_image.setDrawingCacheEnabled(true);
                Bitmap b = viewHolder.item_image.getDrawingCache();
                Bundle bundle = new Bundle();
                bundle.putString("item_name", itemsDatas.get(position).getItemName());
                bundle.putString("item_price", itemsDatas.get(position).getPrice());
                Intent i = new Intent(context, SingleItemDetailShowActivity.class);
                i.putExtra("Bitmap", b);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
*/

        if (SingleRestaurentDetailActivity.counter4 == 0) {
            viewHolder.item_add.setVisibility(View.VISIBLE);
            viewHolder.button_layout.setVisibility(View.GONE);
        } else {
            viewHolder.item_add.setVisibility(View.GONE);
            viewHolder.button_layout.setVisibility(View.VISIBLE);
        }


        viewHolder.img_lov_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_status == false) {
                    // setLikeRequest("0");
                    like_status = true;
                    viewHolder.img_lov_icon.setVisibility(View.GONE);
                    viewHolder.img_lov_icon_select.setVisibility(View.VISIBLE);
                } else {
                    like_status = false;
                    //  setLikeRequest("1");
                    viewHolder.img_lov_icon.setVisibility(View.VISIBLE);
                    viewHolder.img_lov_icon_select.setVisibility(View.GONE);
                }
            }
        });


        viewHolder.item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.item_add.setVisibility(View.GONE);
                viewHolder.button_layout.setVisibility(View.VISIBLE);
                viewHolder.display.setText("1");
                SingleRestaurentDetailActivity.counter4 = SingleRestaurentDetailActivity.counter4 + 1;
                SingleRestaurentDetailActivity.prise4 = SingleRestaurentDetailActivity.prise4 + Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ", ""));
                SingleRestaurentDetailActivity.selected_itemdata4.add(itemsDatas.get(position));
                SingleRestaurentDetailActivity singleRestaurentDetailActivity = new SingleRestaurentDetailActivity();
                singleRestaurentDetailActivity.snackbar(v, context,d_charges);
            }
        });


        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds 1 to the counter
                SingleRestaurentDetailActivity.counter4 = SingleRestaurentDetailActivity.counter4 + 1;
                SingleRestaurentDetailActivity.prise4 = SingleRestaurentDetailActivity.prise4 + Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ", ""));
                viewHolder.display.setText(String.valueOf(Integer.parseInt(viewHolder.display.getText().toString()) + 1));
                SingleRestaurentDetailActivity.selected_itemdata4.add(itemsDatas.get(position));
                SingleRestaurentDetailActivity singleRestaurentDetailActivity = new SingleRestaurentDetailActivity();
                singleRestaurentDetailActivity.snackbar(v, context,d_charges);


            }
        });

        viewHolder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Subtract 1 from counter
                SingleRestaurentDetailActivity.counter4 = SingleRestaurentDetailActivity.counter4 - 1;
                SingleRestaurentDetailActivity.prise4 = SingleRestaurentDetailActivity.prise4 - Integer.parseInt(viewHolder.item_price.getText().toString().replace(" \u20B9 ", ""));

                if (Integer.parseInt(viewHolder.display.getText().toString()) <= 1) {
                    viewHolder.item_add.setVisibility(View.VISIBLE);
                    viewHolder.button_layout.setVisibility(View.GONE);
                }
                SingleRestaurentDetailActivity.selected_itemdata4.remove(itemsDatas.get(position));
                viewHolder.display.setText(String.valueOf(Integer.parseInt(viewHolder.display.getText().toString()) - 1));

                SingleRestaurentDetailActivity singleRestaurentDetailActivity = new SingleRestaurentDetailActivity();
                singleRestaurentDetailActivity.snackbar(v, context,d_charges);

            }
        });

    }


    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView restaurent_card;
        public TextView item_name, item_category, item_price;
        public Button item_add;
        public LinearLayout button_layout;
        public int counter;
        public ImageView item_image, img_icon_veg, img_icon_nonveg,
                img_lov_icon, img_lov_icon_select;
        public TextView add, sub, display;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            //  restaurent_card = (CardView) itemLayoutView.findViewById(R.id.restaurent_card);
            item_image = (ImageView) itemLayoutView.findViewById(R.id.item_image);
            img_icon_nonveg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_nonveg);
            img_icon_veg = (ImageView) itemLayoutView.findViewById(R.id.img_icon_veg);
            img_lov_icon = (ImageView) itemLayoutView.findViewById(R.id.img_lov_icon);
            img_lov_icon_select = (ImageView) itemLayoutView.findViewById(R.id.img_lov_icon_select);
            item_name = (TextView) itemLayoutView.findViewById(R.id.item_name);
            item_category = (TextView) itemLayoutView.findViewById(R.id.item_category);
            item_price = (TextView) itemLayoutView.findViewById(R.id.item_price);
            item_add = (Button) itemLayoutView.findViewById(R.id.item_add);
            button_layout = (LinearLayout) itemLayoutView.findViewById(R.id.button_layout);
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
