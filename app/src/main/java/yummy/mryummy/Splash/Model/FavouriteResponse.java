package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 1/30/2018.
 */

public class FavouriteResponse {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("fItemsData")
    @Expose
    public List<FItemsDatum> fItemsData = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<FItemsDatum> getFItemsData() {
        return fItemsData;
    }

    public void setFItemsData(List<FItemsDatum> fItemsData) {
        this.fItemsData = fItemsData;
    }

}
