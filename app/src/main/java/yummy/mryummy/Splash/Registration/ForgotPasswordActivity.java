package yummy.mryummy.Splash.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.ForgotResponse;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/19/2018.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView txt_login;
    Button login;
    EditText input_phonenbr;
    TextInputLayout input_layout_phone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_layout);

        txt_login = (TextView) findViewById(R.id.txt_login);
        txt_login.setText(Html.fromHtml("<u><b><font color='#FA6C04'>LOGIN</font></b></u>"));
        input_layout_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        input_phonenbr = (EditText) findViewById(R.id.input_phonenbr);
        login = (Button) findViewById(R.id.login);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (input_phonenbr.getText() != null ) {
                            String phnnbr = input_phonenbr.getText().toString().trim();
                            if (phnnbr.length() != 0) {
                                upload(phnnbr);
                            }
                            if (phnnbr.length() == 0) {
                                input_phonenbr.setError("The mobile field is required");
                            }
                        }

                        if (input_phonenbr.getText() == null) {
                            input_phonenbr.setError("The mobile field is required");
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

    private void upload(String phnnbr) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phnnbr);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Mr.Yummy Loading....");
        progressDialog.show();


        ApiUtils.getyummyservice().forgotpassword(map).enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForgotResponse> call, @NonNull Response<ForgotResponse> response) {
                progressDialog.dismiss();

                int getStatus = Integer.parseInt(response.body().getStatuscode());
                String mg = response.body().getMessage();
                try {
                    Log.d("Tag", "response: " + response.body());
                    if(getStatus == 200) {
                        Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Please Try Again...!", Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
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
