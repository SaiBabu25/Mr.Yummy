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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.ForgotResponse;
import yummy.mryummy.Splash.Model.ProfileUpdateResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/19/2018.
 */

public class ProfileUpdateActivity extends AppCompatActivity {

    Button update;
    EditText input_name,input_phone,input_email;
    TextInputLayout input_layout_name,input_layout_phone,input_layout_email;
    private String user_id,user_name,user_mobile,user_email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_layout);

        user_id = Helper.getLocalValue(getApplicationContext(), "user_id");

        user_name = Helper.getLocalValue(getApplicationContext(), "user_name");
        user_mobile = Helper.getLocalValue(getApplicationContext(), "user_mobile");
        user_email = Helper.getLocalValue(getApplicationContext(), "user_email");

        input_layout_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        input_layout_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);

        input_name = (EditText) findViewById(R.id.input_name);
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_email = (EditText) findViewById(R.id.input_email);

        input_name.setText(user_name);
        input_phone.setText(user_mobile);
        input_email.setText(user_email);

        update = (Button) findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (input_name.getText() != null  && input_phone.getText()!= null
                                &&  input_email.getText() != null ) {
                            String firstname = input_name.getText().toString().trim();
                            String phone = input_phone.getText().toString().trim();
                            String email = input_email.getText().toString().trim();

                            if (firstname.length() != 0  || phone.length()  != 0 || email.length() != 0) {
                                upload(firstname,phone,email);
                            }
                            if (firstname.length() == 0) {
                                input_name.setError("The name field is required");
                            } if (phone.length() == 0) {
                                input_phone.setError("The phone field is required");
                            }if (email.length() == 0) {
                                input_email.setError("The email field is required");
                            }
                        }

                        if (input_phone.getText() == null) {
                            input_phone.setError("The mobile field is required");
                        }if (input_email.getText() == null) {
                            input_email.setError("The email field is required");
                        }if (input_name.getText() == null) {
                            input_name.setError("The name field is required");
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

    private void upload(String firstname, String phone, String email) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("name", firstname);
        map.put("phone", phone);
        map.put("email", email);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Mr.Yummy Updating....");
        progressDialog.show();


        ApiUtils.getyummyservice().updateprofile(map).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                progressDialog.dismiss();

                int getStatus = Integer.parseInt(response.body().getStatuscode());
                String mg = response.body().getMessage();
                try {
                    Log.d("Tag", "response: " + response.body());
                    if(getStatus == 200) {
                        Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_SHORT).show();
                        Helper.storeLocally("user_name",response.body().getProfiledata().getName(),getApplicationContext());
                        Helper.storeLocally("user_mobile",response.body().getProfiledata().getMobile(),getApplicationContext());
                        Helper.storeLocally("user_email",response.body().getProfiledata().getEmail(),getApplicationContext());
                        finish();

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "No Update is required with the same input", Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                t.printStackTrace();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                // progressDialog.dismiss();
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Something went wrong...!", Snackbar.LENGTH_LONG)
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

