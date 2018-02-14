package yummy.mryummy.Splash.Activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.app.Activity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Adapter.AllRestaurentListAdapter;
import yummy.mryummy.Splash.Adapter.CooldrinksAdapter;
import yummy.mryummy.Splash.Adapter.RestaurentSlider;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.OrderSuccessActivity;
import yummy.mryummy.Splash.Model.CartItemsResponse;
import yummy.mryummy.Splash.Model.CooldrinksResponse;
import yummy.mryummy.Splash.Model.DrinkCartDatum;
import yummy.mryummy.Splash.Model.ItemsDatum;
import yummy.mryummy.Splash.Model.PaymentOrderResponse;
import yummy.mryummy.Splash.Model.RestaurentResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;
import yummy.mryummy.databinding.ActivityCartBinding;
import yummy.mryummy.databinding.CustomRestaurentItemBinding;

import static yummy.mryummy.Splash.Activity.SingleRestaurentDetailActivity.selected_itemdata;


public class CartActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    int items=0,d_charges=0,cgst=0,sgst=0,rest_id,resturent_id;
    String image;
    String restaurent_ID;
    double gst=0.0,cost=0;
    String amount;
    ActivityCartBinding binding;
    CustomRestaurentItemBinding itemAdapterLayoutBinding;
    ArrayList<String> itemids=new ArrayList<>();
    HashMap<String,Integer> items_count=new HashMap<>();
    List<DrinkCartDatum> drinkcartdata=new ArrayList<>();
    List<ItemsDatum> uniquedata=new ArrayList<>();
    List<ItemsDatum> selected_items=new ArrayList<>();
    private String restaurant_id,user_id,product_quantity,total_amount,
            delivery_charge,mobile,email,deliver_address,cgst1,sgst1,discount,moreinfo,scdl_time,
            payment_type,payment_id;
    EditText moreinfoEditText,time;
    RecyclerView horizontalScrollView1;
    CooldrinksAdapter cooldrinksadapter;
    LinearLayout cooldrink_layout;
    public TextView cooldrinkcharges,cooldrink_charges;

    private String [] product_ids=new String[]{};
    private String restaurantId;
    private String [] productQuantity=new String[]{};
    private String[] product_price=new String[]{};
    private String [] cooldrink_ids=new String[]{};

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;

        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                if (response!=null){
                        Log.e("res",response);
                        String [] parts=response.split(":");
                        String paymentId = Arrays.toString(new String[]
                                {Arrays.toString(new String[]{parts[3]}).split("=")[1]})
                                .replaceAll("[\\[\\]]","");
                        paymentconfirmationmethod(paymentId);
                    }

                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                            .show();
            }

            @Override
            public void onFailure(int code, String reason) {

                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
    private void paymentconfirmationmethod(String payment_id) {
        Log.e("itemIds","ItemIds :"+ Arrays.toString(product_ids).replaceAll("[\\[\\]]",""));
        Log.e("itemQuantity","quantity :"+ Arrays.toString(productQuantity).replaceAll("[\\[\\]]",""));
        Log.e("itemPrice","Price :"+ Arrays.toString(product_price).replaceAll("[\\[\\]]",""));
        HashMap<String, Object> map = new HashMap<>();
        map.put("restaurant_id", restaurantId);
        map.put("user_id", Helper.getLocalValue(getApplicationContext(),"user_id") );
        map.put("product_ids", Arrays.toString(product_ids).replaceAll("[\\[\\]]",""));
        map.put("product_quantity", Arrays.toString(productQuantity).replaceAll("[\\[\\]]",""));
        map.put("product_price", Arrays.toString(product_price).replaceAll("[\\[\\]]",""));
        map.put("total_amount", amount);
        map.put("delivery_charge", d_charges);
        map.put("mobile", Helper.getLocalValue(getApplicationContext(),"user_mobile"));
        map.put("email", "misteryummy@gmail.com" );
        map.put("deliver_address", Helper.getLocalValue(getApplicationContext(),"current_location"));
        map.put("cgst1",  cgst); map.put("sgst1", sgst);
        map.put("discount", "0" );map.put("moreinfo","info");
        map.put("scdl_time", "08:30" );map.put("payment_type", "Online" );
        map.put("payment_id", payment_id);
        Log.e("map",map.toString());
        ApiUtils.getyummyservice().paymentorder(map)
                .enqueue(new Callback<PaymentOrderResponse>() {
                    @Override
                    public void onResponse(Call<PaymentOrderResponse> call, Response<PaymentOrderResponse> response) {
                        if (response!=null && response.body()!=null && response.body().getStatuscode()!=null) {
                            int getStatus = Integer.parseInt(response.body().getStatuscode());
                            String msg = response.body().getMessage();
                            try {
                                if (getStatus == 200) {
                                    PaymentOrderResponse result = response.body();
                                    Log.d("TAG1", "response: " + result);
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), OrderSuccessActivity.class);
                                    startActivity(intent);
                                } else if (getStatus == 400) {
                                    Toast.makeText(getApplicationContext(), "something went wrong..!", Toast.LENGTH_SHORT).show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                    builder.setMessage("No Data")
                                            //  .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (Exception e) {
                                Log.d("TAG", "msg: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentOrderResponse> call, Throwable t) {
                        Log.e("failed",t.getMessage());
                    }
                });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(CartActivity.this,R.layout.activity_cart);
        // Call the function callInstamojo to start payment here
        cooldrink_layout = (LinearLayout) findViewById(R.id.cooldrink_layout);
        cooldrinkcharges = (TextView)  findViewById(R.id.cooldrinkcharges);
        cooldrink_charges = (TextView)  findViewById(R.id.cooldrink_charges);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout1);
        moreinfoEditText = (EditText)  findViewById(R.id.moreinfoEditText);
        moreinfo = binding.moreinfoEditText.getText().toString();

        time = (EditText) findViewById(R.id.time);
        // perform click event listener on edit text
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CartActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        horizontalScrollView1 = (RecyclerView) findViewById(R.id.horizontalScrollView1);
        horizontalScrollView1.setHasFixedSize(true);
        horizontalScrollView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        horizontalScrollView1.setAdapter(cooldrinksadapter); // set the Adapter to RecyclerView

        items=getIntent().getIntExtra("items",0);
        cost=getIntent().getIntExtra("cost",0);
        cgst=getIntent().getIntExtra("cgst",0);
        sgst=getIntent().getIntExtra("sgst",0);
        d_charges=getIntent().getIntExtra("dcharge",0);
        image=getIntent().getStringExtra("rest_pic");
        rest_id = getIntent().getIntExtra("rest_id",0);
        binding.sItems.removeAllViews();
        itemids=new ArrayList<>();
        uniquedata=new ArrayList<>();
        ArrayList<String> ids=new ArrayList<>();
        selected_items.addAll(SingleRestaurentDetailActivity.selected_itemdata);
        selected_items.addAll(SingleRestaurentDetailActivity.selected_itemdata2);
        selected_items.addAll(SingleRestaurentDetailActivity.selected_itemdata3);
        selected_items.addAll(SingleRestaurentDetailActivity.selected_itemdata4);
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
        product_ids=new String[uniquedata.size()];
        productQuantity=new String[uniquedata.size()];
        product_price=new String[uniquedata.size()];
        for (int i=0;i<uniquedata.size();i++) {

            itemAdapterLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),R.layout.custom_restaurent_item,null,false);
            itemAdapterLayoutBinding.itemName.setText( String.valueOf(uniquedata.get(i).getItemName()));
            //   itemAdapterLayoutBinding.item_category.setText(String.valueOf(uniquedata.get(i).getItemSize()));
            itemAdapterLayoutBinding.itemPrice.setText((" \u20B9 "+String.valueOf(uniquedata.get(i).getPrice())));
            itemAdapterLayoutBinding.tvDisplay.setText("Items: "+String.valueOf(items_count.get(uniquedata.get(i).getItemId())));
            binding.sItems.addView(itemAdapterLayoutBinding.getRoot());
            product_ids[i]=uniquedata.get(i).getItemId();
            productQuantity[i]=items_count.get(uniquedata.get(i).getItemId()).toString();
            product_price[i]=uniquedata.get(i).getPrice();
            restaurantId=uniquedata.get(i).getRestaurantId();
            Helper.storeLocally("rest_ID", uniquedata.get(i).getRestaurantId(),getApplicationContext());
            Helper.storeLocally("product_IDS",product_ids[i],getApplicationContext());
            Helper.storeLocally("product_QUANTITY",productQuantity[i],getApplicationContext());
        }
        binding.title.setText("Cart");
        binding.titleText.setText(items+" items, To Pay :  \u20B9 "+(cost+d_charges));
        binding.itemtotal.setText("  \u20B9 "+cost);
        gst=((double)((double)((cgst+sgst)/cost))*100);
        binding.gst.setText("  \u20B9 "+String.valueOf(gst));
        binding.deliveryCharges.setText("  \u20B9 "+d_charges);
        binding.toPay.setText("To Pay "+(cost+gst+d_charges));
       // amount=String.valueOf(cost+gst+d_charges);
        amount="11";
       // binding.cooldrinkCharges.setText("100");

        cooldrinksresponse(restaurantId);
        itemsaddcartmethod(Helper.getLocalValue(getApplicationContext(),"user_id"),restaurantId,
                Helper.getLocalValue(getApplicationContext(),"product_IDS"),
                Helper.getLocalValue(getApplicationContext(),"product_QUANTITY"));

        binding.proceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("phone",Helper.getLocalValue(getApplicationContext(),"user_mobile"));
                Log.e("name",Helper.getLocalValue(getApplicationContext(),"user_name"));

                callInstamojoPay("saicharanlanka1@gmail.com",
                        Helper.getLocalValue(getApplicationContext(), "user_mobile"),
                        amount, "Misteryummy Purchase",
                        Helper.getLocalValue(getApplicationContext(), "user_name"));
            }
        });
        Picasso.with(CartActivity.this).load(image).resize(120, 60)
                .placeholder(R.drawable.app200).into(binding.image);

    }

    private void itemsaddcartmethod(String user_id, String restaurantId, String product_ids, String product_quantity) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("restaurant_id", restaurantId);
        map.put("product_id", product_ids);
        map.put("quantity", product_quantity);

        ApiUtils.getyummyservice().itemscart(map)
                .enqueue(new Callback<CartItemsResponse>() {
                    @Override
                    public void onResponse(Call<CartItemsResponse> call, Response<CartItemsResponse> response) {
                        int getStatus = Integer.parseInt(response.body().getStatuscode());
                        try{
                            if(getStatus == 200){
                                CartItemsResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.cartData != null && result.cartData.size() != 0) {
                                    Toast.makeText(getApplicationContext(), "Item saved to cart", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(getStatus == 400){
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<CartItemsResponse> call, Throwable t) {

                    }
                });

    }

    private void cooldrinksresponse(String rest_id) {
        /*restaurant_id*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("restaurant_id", rest_id);
        ApiUtils.getyummyservice().cooldrinks(map)
                .enqueue(new Callback<CooldrinksResponse>() {
                    @Override
                    public void onResponse(Call<CooldrinksResponse> call, Response<CooldrinksResponse> response) {
                        int getStatus = Integer.parseInt(response.body().getResponseStatus().getStatuscode());
                        try{
                            if(getStatus == 200){
                                CooldrinksResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.drinksdata != null && result.drinksdata.size() != 0) {
                                    cooldrinksadapter = new CooldrinksAdapter(getApplicationContext(),
                                            result.getDrinksdata());
                                    horizontalScrollView1.setAdapter(cooldrinksadapter);

                                  //  cooldrink_layout.setVisibility(View.VISIBLE);

                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                                }

                            }else if(getStatus == 400){
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<CooldrinksResponse> call, Throwable t) {

                    }
                });
    }
}
