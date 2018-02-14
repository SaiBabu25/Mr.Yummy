package yummy.mryummy.Splash.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import yummy.mryummy.R;


/**
 * Created by acer on 11/10/2017.
 */

public class Helper {

    public static void storeLocally(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getLocalValue(Context context, String key) {
        return context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE).getString(key, "");
    }

    public static boolean checkNow(Context con){

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(wifiInfo.isConnected() || mobileInfo.isConnected())
            {
                return true;
            }
        }
        catch(Exception e){
            System.out.println("CheckConnectivity Exception: " + e.getMessage());

        }

        return false;
    }

    public static void showSnackBar(View v, String messgage) {
        try {
            Snackbar.make(v, (CharSequence) messgage, 0).setAction((CharSequence) "Done", null).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUserLoggedIn(Context c) {
        Log.d("Tag","Helpertoken: "+ Helper.getLocalValue(c, "token").toString().trim().length());
        return Helper.getLocalValue(c, "token").toString().trim().length() != 0;
    }
    public static boolean isUserLoggedOut(Context c) {
        Log.d("Tag","Helpertoken: "+ Helper.getLocalValue(c, "token").toString().trim().length());
        return Helper.getLocalValue(c, "token").toString().trim().length() == 0;
    }

    public static String getAuthenticationHeader(Context context) {
        String token = (String) getLocalValue(context, "token");
        return "Bearer " + token;
    }
}
