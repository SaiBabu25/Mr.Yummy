package yummy.mryummy.Splash.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.RegistrationResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 11/25/2017.
 */

public class RegistrationActivity extends AppCompatActivity {

    TextView registration_title,already_login;
    TextInputLayout input_layout_name,input_layout_phone,llEmailNumber,llPassword;
    EditText input_name,input_phone,input_email,input_password;
    Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        registration_title = (TextView) findViewById(R.id.registration_title);
        registration_title.setText(Html.fromHtml("<u><b><font color='#FA6C04'>REGISTRATION</font></b></u>"));
        input_layout_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        input_layout_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        llEmailNumber = (TextInputLayout) findViewById(R.id.input_layout_email);
        llPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_name = (EditText) findViewById(R.id.input_name);
        input_phone = (EditText) findViewById(R.id.input_phone);
        register = (Button) findViewById(R.id.register);

        already_login = (TextView) findViewById(R.id.already_login);
        already_login.setText(Html.fromHtml("Already member?<u><b><font color='#FA6C04'>Login</font></b></u>"));
        already_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Helper.checkNow(RegistrationActivity.this)) {
                        if (input_email.getText() != null && input_password.getText() != null &&
                                input_name.getText() != null && input_phone.getText() != null) {
                            String email = input_email.getText().toString().trim();
                            String password = input_password.getText().toString().trim();
                            String name = input_name.getText().toString().trim();
                            String phone = input_phone.getText().toString().trim();
                            if (email.length() != 0 && password.length() != 0 &&name.length() != 0 &&phone.length() != 0 ) {
                                upload(email,password,name,phone);
                            }
                            if (email.length() == 0) {
                                input_email.setError("The email field is required");
                            }  if (password.length() == 0) {
                                input_password.setError("The password field is required");
                            }  if (name.length() == 0) {
                                input_name.setError("The name field is required");
                            }  if (phone.length() == 0) {
                                input_phone.setError("The phonenumber field is required");
                            }
                        }
                        if (input_email.getText() == null) {
                            input_email.setError("The email field is required");
                        } if (input_password.getText() == null) {
                            input_password.setError("The password field is required");
                        } if (input_name.getText() == null) {
                            input_name.setError("The name field is required");
                        } if (input_phone.getText() == null) {
                            input_phone.setError("The phonenumber field is required");
                        }
                    } else {
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception e) {
                    Log.d("Tag","error: "+e.getMessage());
                }
            }
        });
    }
       private void upload(String email, String password, String name, final String phone) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            map.put("name", name);
            map.put("phone", phone);

            final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Registering...");
            progressDialog.show();

            ApiUtils.getyummyservice().registration(map).enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
                    progressDialog.dismiss();
                    int getStatus = Integer.parseInt(response.body().getStatuscode());
                    try {
                        Log.d("Tag", "response: " + response.body());
                        if (getStatus == 200 ) {
                            Toast.makeText(getApplicationContext(),"Otp is send to registered mobile number",Toast.LENGTH_LONG).show();
                            Intent intent  = new Intent(getApplicationContext(),VerifyOTPActivity.class);
                            intent.putExtra("user_id", response.body().getData().getUserId());
                          //  intent.putExtra("otp", response.body().getData().getOtp());
                            intent.putExtra("otp", response.body().getData().getUserId());
                            intent.putExtra("mobile", phone);
                            startActivity(intent);

                        } else {
                            // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "Please Try Again", Snackbar.LENGTH_LONG)
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
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    t.printStackTrace();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Try Again", Snackbar.LENGTH_LONG)
                            .show();
                }
            });
        }
}
