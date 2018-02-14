package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 1/15/2018.
 */

public class RestaurentItemResponse {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("ItemsData")
    @Expose
    public List<ItemsDatum> itemsData  = new ArrayList<>();

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ItemsDatum> getItemsData() {
        return itemsData;
    }

    public void setItemsData(List<ItemsDatum> itemsData) {
        this.itemsData = itemsData;
    }
}
