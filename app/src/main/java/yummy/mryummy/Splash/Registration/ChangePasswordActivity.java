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

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yummy.mryummy.R;
import yummy.mryummy.Splash.Helper.Helper;
import yummy.mryummy.Splash.Model.ChangePasswordResponse;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.RetrofitServices.ApiUtils;

/**
 * Created by acer on 1/19/2018.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    TextInputLayout input_layout_newpassword,input_layout_oldpassword;
    EditText input_newpassword,input_oldpassword;
    Button change_pwd_btn;
    private String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword_layout);

        user_id = Helper.getLocalValue(getApplicationContext(), "user_id");

        input_layout_newpassword = (TextInputLayout) findViewById(R.id.input_layout_newpassword);
        input_layout_oldpassword = (TextInputLayout) findViewById(R.id.input_layout_oldpassword);
        input_newpassword = (EditText) findViewById(R.id.input_newpassword);
        input_oldpassword = (EditText) findViewById(R.id.input_oldpassword);
        change_pwd_btn = (Button) findViewById(R.id.change_pwd_btn);

        change_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Helper.checkNow(getApplicationContext())) {
                        if (input_oldpassword.getText() != null && input_newpassword.getText() != null) {
                            String oldpwd = input_oldpassword.getText().toString().trim();
                            String newpwd = input_newpassword.getText().toString().trim();
                            //permissionCheck();
                            if (oldpwd.length() != 0 && newpwd.length() != 0 ) {
                                changepaswrd(oldpwd, newpwd);
                            }
                            if (oldpwd.length() == 0) {
                                input_oldpassword.setError("The OldPassword field is required");
                            }
                            else if (newpwd.length() == 0) {
                                input_newpassword.setError("The NewPassword field is required");
                            }
                        }

                        if (input_oldpassword.getText() == null) {
                            input_oldpassword.setError("The OldPassword field is required");
                        }
                        else if (input_newpassword.getText() == null) {
                            input_newpassword.setError("The NewPassword field is required");
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

    private void changepaswrd(String oldpwd, String newpwd) {
        //login
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("old_password", oldpwd);
        map.put("new_password", newpwd);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Changing Password...");
        progressDialog.show();


        ApiUtils.getyummyservice().changepassword(map).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {
                progressDialog.dismiss();
                int getStatus = Integer.parseInt(response.body().getStatuscode());
                try {
                    Log.d("Tag", "response: " + response.body());
                    if (getStatus == 200) {
                      // Helper.storeLocally("address", response.body().getUserData().getAddress(), getApplicationContext());
                        Intent intent  = new Intent(getApplicationContext(),AddressSetActivity.class);
                        startActivity(intent);

                    } else {
                        // Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Invalid", Snackbar.LENGTH_LONG)
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
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
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