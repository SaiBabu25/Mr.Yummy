package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 2/5/2018.
 */

public class CooldrinksResponse {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Drinksdata")
    @Expose
    public List<Drinksdatum> drinksdata = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Drinksdatum> getDrinksdata() {
        return drinksdata;
    }

    public void setDrinksdata(List<Drinksdatum> drinksdata) {
        this.drinksdata = drinksdata;
    }

}
