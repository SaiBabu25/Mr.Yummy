package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acer on 1/21/2018.
 */

public class SearchResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("searchRestaurants")
    @Expose
    public List<SearchRestaurant> searchRestaurants = null;
    @SerializedName("searchitems")
    @Expose
    private List<Searchitem> searchitems = null;

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

    public List<SearchRestaurant> getSearchRestaurants() {
        return searchRestaurants;
    }

    public void setSearchRestaurants(List<SearchRestaurant> searchRestaurants) {
        this.searchRestaurants = searchRestaurants;
    }

    public List<Searchitem> getSearchitems() {
        return searchitems;
    }

    public void setSearchitems(List<Searchitem> searchitems) {
        this.searchitems = searchitems;
    }
}
