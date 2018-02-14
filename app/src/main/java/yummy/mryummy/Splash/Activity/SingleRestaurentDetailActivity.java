package yummy.mryummy.Splash.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Adapter.AllRestaurentItemsListData;
import yummy.mryummy.Splash.Adapter.AllRestaurentItemsListData2;
import yummy.mryummy.Splash.Adapter.AllRestaurentItemsListData3;
import yummy.mryummy.Splash.Adapter.AllRestaurentItemsListData4;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.ItemsDatum;
import yummy.mryummy.Splash.Model.ResponseStatus;
import yummy.mryummy.Splash.Model.RestaurentItemResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/16/2018.
 */

public class SingleRestaurentDetailActivity extends AppCompatActivity {


    public static int counter = 0;
    public static int counter2 = 0;
    public static int prise = 0;
    public static int prise2 = 0;
    public static int counter3 = 0;
    public static int counter4 = 0;
    public static int prise3 = 0;
    public static int prise4 = 0;
    private int prise5=0;
    private int counter5=0;
    private boolean snackinitialise=false;
    Snackbar snackbar = null;
    public static List<ItemsDatum> selected_itemdata = new ArrayList<ItemsDatum>();
    public static List<ItemsDatum> selected_itemdata2 = new ArrayList<ItemsDatum>();
    public static List<ItemsDatum> selected_itemdata3 = new ArrayList<ItemsDatum>();
    public static List<ItemsDatum> selected_itemdata4 = new ArrayList<ItemsDatum>();

    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;
   // ShimmerRecyclerView shimmerRecycler1,shimmerRecycler2,shimmerRecycler3,shimmerRecycler4;
    AllRestaurentItemsListData allrestaurentitemsAdapter;
    AllRestaurentItemsListData2 allrestaurentitemsAdapter2;
    AllRestaurentItemsListData3 allrestaurentitemsAdapter3;
    AllRestaurentItemsListData4 allrestaurentitemsAdapter4;
    TextView recomended_text,recomended_text2;

    List<ItemsDatum> itemsdatalist =   new ArrayList<ItemsDatum>();;
    List<ItemsDatum> veglist=new ArrayList<>();
    List<ItemsDatum> veglist_woimage=new ArrayList<>();
    List<ItemsDatum> nonveglist=new ArrayList<>();
    List<ItemsDatum> nonveglist_woimage=new ArrayList<>();

    List<ResponseStatus> responsestatues = new ArrayList<ResponseStatus>();;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String userid,id,restname,restreview,restdistance,restcategory,restcharge="0",sgst="0",cgst="0";
    TextView rating,timeValue,priceValue;
    ImageView restPic;
    String image;
    RadioButton veg,nonveg;
    RadioGroup catgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_restaurent_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout1);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
                onBackPressed();
            }
        });

      /*  toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), AllRestaurentsListActivity.class);
                startActivity(myIntent);
            }
        });*/
        counter=0;
        counter2=0;
        counter3=0;
        counter4=0;
        prise=0;
        prise2=0;
        prise3=0;
        prise4=0;
        selected_itemdata.clear();
        selected_itemdata2.clear();
        selected_itemdata3.clear();
        selected_itemdata4.clear();

        userid = Helper.getLocalValue(getApplicationContext(),"user_id");

        rating = (TextView)  findViewById(R.id.rating);
        timeValue = (TextView)  findViewById(R.id.time);
        priceValue = (TextView)  findViewById(R.id.price);
        catgroup= (RadioGroup) findViewById(R.id.toggle);
        veg= (RadioButton) findViewById(R.id.veg);
        nonveg= (RadioButton) findViewById(R.id.offer);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id = arguments.getString("rest_id");
            restname = arguments.getString("rest_name");
            restreview = arguments.getString("rest_review");
            restdistance = arguments.getString("rest_distance");
            restcategory = arguments.getString("rest_category");
            restcharge = arguments.getString("rest_charge");
            sgst=arguments.getString("sgst");
            cgst=arguments.getString("cgst");
            image=arguments.getString("rest_pic");
            Bitmap bitmap;

            Picasso.with(SingleRestaurentDetailActivity.this).load(image).resize(120, 60)
                    .placeholder(R.drawable.app200).into(target);
            // restPic = arguments.getString("rest_image");
        }
        collapsingToolbarLayout.setTitle(restname);

        setSupportActionBar(toolbar);
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Integer.parseInt("#FFFFFF"));
        rating.setText(restreview);
        timeValue.setText(restdistance);
        priceValue.setText(" \u20B9 "+restcharge+"/-");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
      /*  shimmerRecycler1 = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_first);
        shimmerRecycler1.showShimmerAdapter();
        shimmerRecycler2 = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_second);
        shimmerRecycler2.showShimmerAdapter();
        shimmerRecycler3 = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_three);
        shimmerRecycler3.showShimmerAdapter();
        shimmerRecycler4 = (ShimmerRecyclerView)findViewById(R.id.shimmer_recycler_view_four);
        shimmerRecycler4.showShimmerAdapter();*/
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView4 = (RecyclerView) findViewById(R.id.recyclerView4);
        recyclerView4.setHasFixedSize(true);
        recomended_text = (TextView) findViewById(R.id.recommended_count);
        recomended_text2 = (TextView) findViewById(R.id.recommended_count2);

           GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
       // shimmerRecycler1.setAdapter(allrestaurentitemsAdapter);
        // set the Adapter to RecyclerView
        singlerestaurentid(id,userid);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(),2);
        recyclerView2.setLayoutManager(gridLayoutManager2);
      //  shimmerRecycler2.setAdapter(allrestaurentitemsAdapter2);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getApplicationContext(),1);
        recyclerView3.setLayoutManager(gridLayoutManager3);
      //  shimmerRecycler3.setAdapter(allrestaurentitemsAdapter3);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getApplicationContext(),1);
        recyclerView4.setLayoutManager(gridLayoutManager4);
      //  shimmerRecycler4.setAdapter(allrestaurentitemsAdapter4);
    }
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // edit your bitmap and set as background
            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(ob);

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    private void singlerestaurentid(String id,String user_id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("restaurant_id", id);
        map.put("user_id", user_id);

        final ProgressDialog progressDialog = new ProgressDialog(SingleRestaurentDetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ApiUtils.getyummyservice().restaurentitemslist(map)
                .enqueue(new Callback<RestaurentItemResponse>() {
                    @Override
                    public void onResponse(Call<RestaurentItemResponse> call, Response<RestaurentItemResponse> response) {
                        progressDialog.dismiss();
                        int getStatus = Integer.parseInt(response.body().getResponseStatus().getStatuscode());
                        try{
                            if(getStatus == 200){
                                final RestaurentItemResponse result = response.body();
                                Log.d("TAG1","response: "+result);
                                if (result != null && result.itemsData != null && result.itemsData.size() != 0) {
                                    for (int i=0;i<result.itemsData.size();i++){
                                        if (result.getItemsData().get(i).getItemType().equals("Veg")){
                                            Log.e("iimage",result.getItemsData().get(i).getItemImage());
                                            if (result.getItemsData().get(i).getItemImage().equals("https://openingsin.com/mryummy_admin/")){
                                                veglist_woimage.add(result.getItemsData().get(i));
                                            }else {
                                                veglist.add(result.getItemsData().get(i));
                                            }
                                        }else {
                                            if (result.getItemsData().get(i).getItemImage().equals("https://openingsin.com/mryummy_admin/")){
                                                nonveglist_woimage.add(result.getItemsData().get(i));
                                            }else {
                                                nonveglist.add(result.getItemsData().get(i));
                                            }
                                        }
                                    }
                                    recomended_text.setText("("+nonveglist.size()+")");
                                    recomended_text2.setText("("+nonveglist_woimage.size()+")");
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView2.setVisibility(View.GONE);
                                    recyclerView3.setVisibility(View.VISIBLE);
                                    recyclerView4.setVisibility(View.GONE);

                                    allrestaurentitemsAdapter = new AllRestaurentItemsListData(getApplicationContext(),
                                            nonveglist,result.getResponseStatus(),sgst,cgst,restcharge,image);
                                    recyclerView.setAdapter(allrestaurentitemsAdapter);

                                    allrestaurentitemsAdapter2 = new AllRestaurentItemsListData2(getApplicationContext(),
                                            veglist,result.getResponseStatus(),sgst,cgst,restcharge,image);
                                    recyclerView2.setAdapter(allrestaurentitemsAdapter2);

                                    allrestaurentitemsAdapter3 = new AllRestaurentItemsListData3(getApplicationContext(),
                                            nonveglist_woimage,result.getResponseStatus(),sgst,cgst,restcharge,image);
                                    recyclerView3.setAdapter(allrestaurentitemsAdapter3);

                                    allrestaurentitemsAdapter4 = new AllRestaurentItemsListData4(getApplicationContext(),
                                            veglist_woimage,result.getResponseStatus(),sgst,cgst,restcharge,image);
                                    recyclerView4.setAdapter(allrestaurentitemsAdapter4);

                                    catgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                            if (i==veg.getId()){
                                                recyclerView.setVisibility(View.GONE);
                                                recyclerView2.setVisibility(View.VISIBLE);
                                                recyclerView3.setVisibility(View.GONE);
                                                recyclerView4.setVisibility(View.VISIBLE);
                                                recomended_text.setText("("+veglist.size()+")");
                                                recomended_text2.setText("("+veglist_woimage.size()+")");

                                            }else {
                                                recyclerView.setVisibility(View.VISIBLE);
                                                recyclerView2.setVisibility(View.GONE);
                                                recyclerView3.setVisibility(View.VISIBLE);
                                                recyclerView4.setVisibility(View.GONE);
                                                recomended_text.setText("("+nonveglist.size()+")");
                                                recomended_text2.setText("("+nonveglist_woimage.size()+")");
                                                allrestaurentitemsAdapter = new AllRestaurentItemsListData(getApplicationContext(),
                                                       nonveglist,result.getResponseStatus(),sgst,cgst,restcharge,image);
                                                recyclerView.setAdapter(allrestaurentitemsAdapter);
                                            }
                                        }
                                    });

                                }else{
                                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_SHORT).show();
                                }

                            }else if(response.body().getResponseStatus().getStatuscode() == "400"){
                                Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("No Data")
                                        //  .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                               // Toast.makeText(getApplicationContext(), "Please Try Again..!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.d("TAG","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurentItemResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }
    public void snackbar(View v, final Context context, final String rest_charg) {
        if (!snackinitialise) {
            counter5=counter+counter2+counter3+counter4;
            prise5=prise+prise2+prise3+prise4;
            if (cgst!=null) {
                if (cgst.equals("null") ||cgst.equals("")) {
                    cgst="0";
                }
            }else {
                cgst="0";
            }
            if (sgst!=null) {
                if (sgst.equals("null") ||sgst.equals("")) {
                    sgst="0";
                }
            }else {
                sgst="0";
            }

            snackinitialise=true;
            snackbar = Snackbar.make(v, counter5 + " items |" + " \u20B9 " +prise5, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(context.getResources().getColor(R.color.white));

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            final Snackbar finalSnackbar = snackbar;
            snackbar.setAction("VIEW CART", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalSnackbar.dismiss();
                    Intent intent=new Intent(context,CartActivity.class);
                    intent.putExtra("items",counter5);
                    intent.putExtra("cost",prise5);
                    intent.putExtra("cgst",Integer.parseInt(cgst));
                    intent.putExtra("sgst",Integer.parseInt(sgst));
                    intent.putExtra("dcharge",Integer.parseInt(rest_charg));
                    intent.putExtra("rest_pic",image);
                    intent.putExtra("rest_id",id);
                    intent.putExtra("product_ids",id);
                    intent.putExtra("product_quantity",id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                   /* ((Activity)context).finish();*/
                }
            });
            snackbar.show();
        }else {
            snackbar.setText(counter5 + " items |" + " \u20B9 " + prise5);
        }
        if (prise5==0){
            snackbar.dismiss();
        }else {
            snackbar.show();
        }
    }
}
