package yummy.mryummy.Splash.Registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Home.HomeActivity;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.Model.RegistrationResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 11/25/2017.
 */

public class LoginActivity extends AppCompatActivity {

    TextView login_title,forgetpassword,txt_reg;
    TextInputLayout llEmailNumber,llPassword;
    EditText input_email,input_password;
    Button login;

    public static final String PREFS_NAME = "LoginPrefs";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        txt_reg = (TextView) findViewById(R.id.txt_reg);
        login_title = (TextView) findViewById(R.id.login_title);
        login_title.setText(Html.fromHtml("<u><b><font color='#FA6C04'>LOGIN</font></b></u>"));
        llEmailNumber = (TextInputLayout) findViewById(R.id.input_layout_email);
        llPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.login);

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (input_password.getText() != null && input_email.getText() != null) {
                            String passwrd = input_password.getText().toString().trim();
                            String emal = input_email.getText().toString().trim();
                            //permissionCheck();
                            if (passwrd.length() != 0 && emal.length() != 0 ) {
                                upload(emal, passwrd);
                            }
                            if (emal.length() == 0) {
                                input_email.setError("The mobile field is required");
                            }
                            else if (passwrd.length() == 0) {
                                input_password.setError("The password field is required");
                            }
                        }

                        if (input_email.getText() == null) {
                            input_email.setError("The mobile field is required");
                        }
                        else if (input_password.getText() == null) {
                            input_password.setError("The password field is required");
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

    private void upload(String emal, String passwrd) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", emal);
        map.put("password", passwrd);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logingin...");
        progressDialog.show();


        ApiUtils.getyummyservice().login(map).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Tag", "response: " + response.body());
                    if (response.body().getToken() != null ) {

                        Helper.storeLocally("token", response.body().getToken(), getApplicationContext());
                        Helper.storeLocally("user_id", response.body().getUserData().getUserId(), getApplicationContext());
                      //  Helper.storeLocally("login_address", response.body().getUserData().getAddress(), getApplicationContext());
                        Helper.storeLocally("login_address", Helper.getLocalValue(getApplicationContext(),"location_store"), getApplicationContext());
                        Helper.storeLocally("user_name",response.body().getUserData().getUsername(),getApplicationContext());
                        Helper.storeLocally("user_mobile",response.body().getUserData().getMobile(),getApplicationContext());
                        Helper.storeLocally("user_email",response.body().getUserData().getEmail(),getApplicationContext());
                        Helper.storeLocally("user_image",response.body().getUserData().getImage(),getApplicationContext());

                     /*   Intent intent  = new Intent(getApplicationContext(),AddressSetActivity.class);
                        startActivity(intent);*/
                        Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Invalid Credentials", Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finish();
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

    public void onSuperBackPressed(){
        super.onBackPressed();
    }
}

