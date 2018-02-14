package yummy.mryummy.Splash.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acer on 1/31/2018.
 */

public class LikeResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("frtData")
    @Expose
    private FrtData frtData;
    @SerializedName("status")
    @Expose
    private Integer status;

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

    public FrtData getFrtData() {
        return frtData;
    }

    public void setFrtData(FrtData frtData) {
        this.frtData = frtData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
