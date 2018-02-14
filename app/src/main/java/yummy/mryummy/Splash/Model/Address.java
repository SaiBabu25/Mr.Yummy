package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 1/19/2018.
 */

public class Address {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("flat")
    @Expose
    private String flat;
    @SerializedName("landmark")
    @Expose
    private String landmark;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
