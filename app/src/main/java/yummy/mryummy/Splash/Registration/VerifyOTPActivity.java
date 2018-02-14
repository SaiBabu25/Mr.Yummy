package yummy.mryummy.Splash.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.Model.OTPResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 11/25/2017.
 */

public class VerifyOTPActivity extends AppCompatActivity {

    TextView verify_title,customer_phnnbr,four_digits,enterotp_title,verify_call;
    ImageView image_location;
    EditText otp_code_1,otp_code_2,otp_code_3,otp_code_4;
    Button proceed;
    String userid,otp,nbr;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifyotp_layout);

        try{
            Bundle b =getIntent().getExtras();
            userid = b.getString("user_id");
            otp = b.getString("otp");
            nbr = b.getString("mobile");
        } catch (NullPointerException npe) {

        } catch (NumberFormatException nfe) {

        } catch (Exception e) {

        }

        verify_title = (TextView) findViewById(R.id.verify_title);
        verify_title.setText(Html.fromHtml("<u><b><font color='#000000'>VERFIFY DETAILS</font></b></u>"));
        customer_phnnbr = (TextView) findViewById(R.id.customer_phnnbr);
        customer_phnnbr.setText("+91 "+nbr);
        four_digits = (TextView) findViewById(R.id.four_digits);
        image_location = (ImageView) findViewById(R.id.image_location);
        enterotp_title = (TextView) findViewById(R.id.enterotp_title);
        //verify_call = (TextView) findViewById(R.id.verify_call);

        otp_code_1 = (EditText) findViewById(R.id.otp_code_1);
      /*  otp_code_2 = (EditText) findViewById(R.id.otp_code_2);
        otp_code_3 = (EditText) findViewById(R.id.otp_code_3);
        otp_code_4 = (EditText) findViewById(R.id.otp_code_4);*/
        proceed = (Button) findViewById(R.id.proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (otp_code_1.getText() != null) {
                            String otp = otp_code_1.getText().toString().trim();
                            if (otp.length() != 0 ) {
                                upload(otp);
                            }
                            if (otp.length() == 0) {
                                otp_code_1.setError("The OTP field is required");
                            }
                        }
                        if (otp_code_1.getText() == null) {
                            otp_code_1.setError("The OTP field is required");
                        }

                    } else {
                        //  Helper.showSnackBar(LoginActivity.this.getView(), "No internet connection");
                        //  Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAction("Try Again", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                                .show();
                    }
                } catch (Exception e) {
                    Log.d("Tag","error: "+e.getMessage());
                }
            }
        });
    }

    private void upload(String otp) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("otp", otp);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying...");
        progressDialog.show();


        ApiUtils.getyummyservice().otp_verify(map).enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(@NonNull Call<OTPResponse> call, @NonNull Response<OTPResponse> response) {
                progressDialog.dismiss();
                int getStatus = Integer.parseInt(response.body().getStatuscode());
                try {
                    Log.d("Tag", "response: " + response.body());
                    if (getStatus == 200) {
                        Helper.storeLocally("user_id", response.body().getOtpData().getUserId(), getApplicationContext());
                        Helper.storeLocally("username", response.body().getOtpData().getUsername(), getApplicationContext());
                        Helper.storeLocally("mobile", response.body().getOtpData().getMobile(), getApplicationContext());
                        Helper.storeLocally("email", response.body().getOtpData().getEmail(), getApplicationContext());

                        Helper.storeLocally("user_name",response.body().getOtpData().getUsername(),getApplicationContext());
                        Helper.storeLocally("user_mobile",response.body().getOtpData().getMobile(),getApplicationContext());
                        Helper.storeLocally("user_email",response.body().getOtpData().getEmail(),getApplicationContext());

                        Intent intent  = new Intent(getApplicationContext(),AddressSetActivity.class);
                        startActivity(intent);

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Invalid OTP", Snackbar.LENGTH_LONG)
                                .setAction("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                                .show();
                        //  Helper.showSnackBar(LoginFragment.this.getView(),"Invalid Credentials");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                t.printStackTrace();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // progressDialog.dismiss();
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Invalid Credentials", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary ))
                        .show();
            }
        });
    }
}

