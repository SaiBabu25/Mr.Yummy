package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 1/15/2018.
 */

public class ItemsDatum {

    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("item_category")
    @Expose
    private String itemCategory;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("item_size")
    @Expose
    private String itemSize;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("item_status")
    @Expose
    private String itemStatus;
    @SerializedName("item_favourite_status")
    @Expose
    private Integer itemFavouriteStatus;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getItemFavouriteStatus() {
        return itemFavouriteStatus;
    }

    public void setItemFavouriteStatus(Integer itemFavouriteStatus) {
        this.itemFavouriteStatus = itemFavouriteStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
