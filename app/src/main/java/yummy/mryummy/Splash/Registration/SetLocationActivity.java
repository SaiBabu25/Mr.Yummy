package yummy.mryummy.Splash.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Home.HomeActivity;

/**
 * Created by acer on 11/25/2017.
 */

public class SetLocationActivity extends AppCompatActivity {
Button set_location;
    TextView ready_to_order;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlocation_layout);

        set_location = (Button) findViewById(R.id.set_location);
        ready_to_order = (TextView) findViewById(R.id.ready_to_order);

        ready_to_order.setText(Html.fromHtml("<u><b><font color='#FA6C04'>Ready to Order?</font></b></u>"));

        set_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),AddressSetActivity.class);
                startActivity(intent);
            }
        });
    }
}
