package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 1/31/2018.
 */

public class FrtData {

    @SerializedName("favourite_id")
    @Expose
    private String favouriteId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    public String getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(String favouriteId) {
        this.favouriteId = favouriteId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
