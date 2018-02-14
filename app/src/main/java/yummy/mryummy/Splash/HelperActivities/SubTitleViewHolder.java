package yummy.mryummy.Splash.HelperActivities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Registration.ChangePasswordActivity;

/**
 * Created by sunil on 11/28/16.
 */

public class SubTitleViewHolder extends ChildViewHolder {

    private TextView subTitleTextView;
    private Context context;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitleTextView = (TextView) itemView.findViewById(R.id.subtitle);

    }

    public void setSubTitletName(String name) {
        subTitleTextView.setText(name);
    }
}
