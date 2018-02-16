package yummy.mryummy.Splash.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Activity.FavouritesActivity;
import yummy.mryummy.Splash.Activity.FiltersActivity;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.HelperActivities.Constant;
import yummy.mryummy.Splash.HelperActivities.RecyclerAdapter;
import yummy.mryummy.Splash.HelperActivities.SubTitle;
import yummy.mryummy.Splash.HelperActivities.Title;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Registration.AddressSetActivity;
import yummy.mryummy.Splash.Registration.ChangePasswordActivity;
import yummy.mryummy.Splash.Registration.LoginActivity;
import yummy.mryummy.Splash.Registration.ProfileUpdateActivity;

/**
 * Created by acer on 1/19/2018.
 */

public class MyAccountFragment extends Fragment {

    public TextView text_prof_name,text_prof_edit,text_prof_number,divide,text_prof_email,text_logout;
    public String name,number,email;
    public TextView text_favourites,text_change_pwd,text_myaccount;
    LinearLayout linear_help;
    ImageView img_logout;

    //  @BindView(R.id.recyclerview_expand)
    RecyclerView recyclerview_expand;
    String names[] = Constant.name;
    String subNames[] = Constant.subName;

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = Helper.getLocalValue(getActivity(), "user_name");
        number = Helper.getLocalValue(getActivity(), "user_mobile");
        email = Helper.getLocalValue(getActivity(), "user_email");

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myaccount_layout, container, false);

        text_prof_name = (TextView) view.findViewById(R.id.text_prof_name);
        text_prof_edit = (TextView) view.findViewById(R.id.text_prof_edit);
        text_prof_number = (TextView) view.findViewById(R.id.text_prof_number);
        divide = (TextView) view.findViewById(R.id.divide);
        text_prof_email = (TextView) view.findViewById(R.id.text_prof_email);
        text_logout = (TextView) view.findViewById(R.id.text_logout);

      //  text_manage_address = (TextView) view.findViewById(R.id.text_manage_address);
        text_favourites = (TextView) view.findViewById(R.id.text_favourites);
        text_change_pwd = (TextView) view.findViewById(R.id.text_change_pwd);
        text_myaccount = (TextView) view.findViewById(R.id.text_myaccount);
        img_logout = (ImageView)  view.findViewById(R.id.img_logout);

      /*  text_manage_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), AddressSetActivity.class);
                startActivity(intent);
            }
        });*/

        text_favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), FavouritesActivity.class);
                startActivity(intent);
            }
        });

        text_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        text_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), ProfileUpdateActivity.class);
                startActivity(intent);
            }
        });


       /* recyclerview_expand = (RecyclerView) view.findViewById(R.id.recyclerview_expand);
        ButterKnife.bind(getActivity());
        List list = getList();
        recyclerview_expand.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), list);
        recyclerview_expand.setAdapter(adapter);
        recyclerview_expand.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview_expand.setAdapter(adapter);*/

        text_prof_name.setText(name);
        text_prof_number.setText(number);
        text_prof_email.setText(email);

        text_prof_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent(getActivity(), ProfileUpdateActivity.class);
                startActivity(intent);
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper.storeLocally("user_id","",getActivity());
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Helper.storeLocally("user_id","null",getActivity());
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return  view;
    }

    private List getList() {
        List list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            List subTitles = new ArrayList<>();
            for (int j = 0; j< subNames.length; j++){
                SubTitle subTitle = new SubTitle(subNames[j]);
                subTitles.add(subTitle);
            }
            Title model = new Title(names[i],subTitles);
            list.add(model);
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();

       /* FragmentTransaction fragment=getFragmentManager().beginTransaction();
        fragment.attach()*/


       /* Fragment frg = getFragmentManager().findFragmentByTag("MyAccountFragment");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/

    }
}

