package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 1/15/2018.
 */

public class RestaurantsDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("cgst")
    @Expose
    private Object cgst;
    @SerializedName("sgst")
    @Expose
    private Object sgst;
    @SerializedName("morning_time")
    @Expose
    private Object morningTime;
    @SerializedName("evening_time")
    @Expose
    private Object eveningTime;
    @SerializedName("restaurant_status")
    @Expose
    private Object restaurantStatus;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("created_on")
    @Expose
    private Object createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Object getCgst() {
        return cgst;
    }

    public void setCgst(Object cgst) {
        this.cgst = cgst;
    }

    public Object getSgst() {
        return sgst;
    }

    public void setSgst(Object sgst) {
        this.sgst = sgst;
    }

    public Object getMorningTime() {
        return morningTime;
    }

    public void setMorningTime(Object morningTime) {
        this.morningTime = morningTime;
    }

    public Object getEveningTime() {
        return eveningTime;
    }

    public void setEveningTime(Object eveningTime) {
        this.eveningTime = eveningTime;
    }

    public Object getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(Object restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

}
