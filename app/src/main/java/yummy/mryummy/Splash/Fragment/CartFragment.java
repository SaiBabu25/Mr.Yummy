package yummy.mryummy.Splash.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.HelperActivities.Constant;
import yummy.mryummy.Splash.HelperActivities.RecyclerAdapter;
import yummy.mryummy.Splash.HelperActivities.SubTitle;
import yummy.mryummy.Splash.HelperActivities.Title;
import yummy.mryummy.Splash.Registration.ProfileUpdateActivity;

/**
 * Created by acer on 1/21/2018.
 */

public class CartFragment extends Fragment {

    public TextView text_prof_name,text_prof_edit,text_prof_number,divide,text_prof_email,text_logout;
    public String name,number,email;
    LinearLayout linear_help;

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

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return  view;
    }


}


