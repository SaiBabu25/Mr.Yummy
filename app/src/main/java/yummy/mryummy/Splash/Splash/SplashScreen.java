package yummy.mryummy.Splash.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Registration.LoginActivity;


public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    String userid;
    ImageView splash_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        userid = Helper.getLocalValue(getApplicationContext(), "user_id");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your app main activity
                if (/*Helper.getLocalValue(getApplicationContext(), "user_id").equals("null")
                        && Helper.getLocalValue(getApplicationContext(), "user_id").equals("")*/
                userid == ""){
                    Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
               /* Intent i = new Intent(SplashScreen.this, StartPage.class);
                startActivity(i);
                finish();*/
            }
        }, SPLASH_TIME_OUT);
    }

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Helper.isUserLoggedIn(SplashScreen.this))
                {
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(SplashScreen.this, StartPage.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }*/

   }