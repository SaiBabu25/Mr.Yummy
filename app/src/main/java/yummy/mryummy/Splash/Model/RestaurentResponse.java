package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 1/15/2018.
 */

public class RestaurentResponse {

    @SerializedName("Restaurantlist")
    @Expose
    private Restaurantlist restaurantlist;
    @SerializedName("RestaurantsData")
    @Expose
    public List<RestaurantsDatum> restaurantsData = null;
    @SerializedName("Sliderset")
    @Expose
    private List<Sliderset> sliderset = null;

    public Restaurantlist getRestaurantlist() {
        return restaurantlist;
    }

    public void setRestaurantlist(Restaurantlist restaurantlist) {
        this.restaurantlist = restaurantlist;
    }

    public List<RestaurantsDatum> getRestaurantsData() {
        return restaurantsData;
    }

    public void setRestaurantsData(List<RestaurantsDatum> restaurantsData) {
        this.restaurantsData = restaurantsData;
    }

    public List<Sliderset> getSliderset() {
        return sliderset;
    }

    public void setSliderset(List<Sliderset> sliderset) {
        this.sliderset = sliderset;
    }
}
