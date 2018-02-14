package yummy.mryummy.Splash.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Registration.AddressSetActivity;
import yummy.mryummy.Splash.Registration.LoginActivity;
import yummy.mryummy.Splash.Registration.RegistrationActivity;

/**
 * Created by acer on 11/25/2017.
 */

public class StartPage extends AppCompatActivity{
    TextView new_user;
    Button login;
    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        userid = Helper.getLocalValue(getApplicationContext(), "user_id");

       /* if (Helper.isUserLoggedIn(StartPage.this)){*/
        if (userid.equals("null") && userid.equals("")){
            Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }

        new_user = (TextView) findViewById(R.id.new_user);
        new_user.setText(Html.fromHtml("New User? <u><b><font color='#F56F2A'>Register</font></b></u>"));
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
