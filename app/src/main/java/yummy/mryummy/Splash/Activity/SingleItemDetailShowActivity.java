package yummy.mryummy.Splash.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import yummy.mryummy.R;

/**
 * Created by acer on 1/18/2018.
 */

public class SingleItemDetailShowActivity extends AppCompatActivity {

    ImageView imageviewnew;
    TextView item_name,bSub,tvDisplay,bAdd;
    Button item_price,item_add;
    LinearLayout button_layout;
    String itemname,itemprice,itemcount,itemcost,itemcgst,itemsgst,itemdcharge,itemimage="null";
    private int counter = 0;
    private int prise = 0;
    private boolean snackinitialise=false;
    Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_show_layout);

        item_name = (TextView)  findViewById(R.id.item_name);
        item_price = (Button)  findViewById(R.id.item_price);
        bSub = (TextView)  findViewById(R.id.bSub);
        tvDisplay = (TextView)  findViewById(R.id.tvDisplay);
        bAdd = (TextView)  findViewById(R.id.bAdd);
        item_add = (Button)  findViewById(R.id.item_add);
        button_layout = (LinearLayout) findViewById(R.id.button_layout);
        imageviewnew = (ImageView)  findViewById(R.id.imageviewnew);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            itemname = arguments.getString("item_name");
            itemprice = arguments.getString("item_price");
           // bundle.putString("rest_pic",image);
            itemcount = arguments.getString("items");
            itemcost = arguments.getString("cost");
            itemcgst  = arguments.getString("cgst");
            itemsgst  = arguments.getString("sgst");
            itemdcharge = arguments.getString("dcharge");
            itemimage = arguments.getString("rest_pic");
            Bitmap bitmap;
            Picasso.with(SingleItemDetailShowActivity.this).load(itemimage).resize(120, 60)
                    .placeholder(R.drawable.app200).into(target);

        }

        Bitmap bitmap = (Bitmap) this.getIntent().getParcelableExtra("Bitmap");
        imageviewnew.setImageBitmap(bitmap);

        item_name.setText(itemname);
        item_price.setText(" \u20B9 "+itemprice);


        item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_add.setVisibility(View.GONE);
                button_layout.setVisibility(View.VISIBLE);
                tvDisplay.setText("1");
                counter=counter+1;
                prise=prise+Integer.parseInt(item_price.getText().toString().replace(" \u20B9 ",""));
                snackbar(v);
            }
        });


        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds 1 to the counter
                counter=counter+1;
                prise=prise+Integer.parseInt(item_price.getText().toString().replace(" \u20B9 ",""));
                tvDisplay.setText(String.valueOf(Integer.parseInt(tvDisplay.getText().toString())+1));
                snackbar(v);


            }
        });

        bSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Subtract 1 from counter
                counter = counter - 1;
                prise=prise-Integer.parseInt(item_price.getText().toString().replace(" \u20B9 ",""));
                snackbar(v);
                if (Integer.parseInt(tvDisplay.getText().toString()) <= 1) {
                    item_add.setVisibility(View.VISIBLE);
                    button_layout.setVisibility(View.GONE);
                }

                tvDisplay.setText(String.valueOf(Integer.parseInt(tvDisplay.getText().toString())-1));

            }
        });



    }
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // edit your bitmap and set as background
            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
            // collapsingToolbarLayout.setBackground(ob);

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    /*private void snackbar(View v) {
        if (!snackinitialise) {
            snackinitialise=true;
            snackbar = Snackbar.make(v, counter + " items added to cart  " + " \u20B9 " + prise, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getApplicationContext().getResources().getColor(R.color.white));

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
            final Snackbar finalSnackbar = snackbar;
            snackbar.setAction("VIEW CART", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalSnackbar.dismiss();
                    //  context.startActivity(new Intent(context,CartActivity.class));
                    Intent i = new Intent(getApplicationContext(), CartActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    // ((Activity)context).finish();
                }
            });
            snackbar.show();
        }else {
            snackbar.setText(counter + " items added to cart  " + " \u20B9 " + prise);
        }
        if (prise==0){
            snackbar.dismiss();
        }else {
            snackbar.show();
        }
    }*/

    private void snackbar(View v) {
        if (!snackinitialise) {
            snackinitialise=true;
            snackbar = Snackbar.make(v, counter + " items |" + " \u20B9 " + prise, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getApplicationContext().getResources().getColor(R.color.white));

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
            final Snackbar finalSnackbar = snackbar;
            snackbar.setAction("VIEW CART", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalSnackbar.dismiss();
                    Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                    intent.putExtra("items",counter);
                    intent.putExtra("cost",prise);
                    intent.putExtra("cgst",Integer.parseInt(itemcgst));
                    intent.putExtra("sgst",Integer.parseInt(itemsgst));
                    intent.putExtra("dcharge",Integer.parseInt(itemdcharge));
                    intent.putExtra("rest_pic",itemimage);

                    // intent.putExtra("s_item_data", (Parcelable) selected_itemdata);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
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

}
