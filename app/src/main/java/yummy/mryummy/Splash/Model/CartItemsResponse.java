package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 2/10/2018.
 */

public class CartItemsResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("CartData")
    @Expose
    public List<CartDatum> cartData = null;

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

    public List<CartDatum> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
    }

}
