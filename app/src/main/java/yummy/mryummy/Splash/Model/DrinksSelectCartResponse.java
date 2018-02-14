package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 2/9/2018.
 */

public class DrinksSelectCartResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("drinkCartData")
    @Expose
    public List<DrinkCartDatum> drinkCartData = null;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DrinkCartDatum> getDrinkCartData() {
        return drinkCartData;
    }

    public void setDrinkCartData(List<DrinkCartDatum> drinkCartData) {
        this.drinkCartData = drinkCartData;
    }

}
