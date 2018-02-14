package yummy.mryummy.Splash.RetrofitServices;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yummy.mryummy.Splash.Model.AddressRespponse;
import yummy.mryummy.Splash.Model.CartItemsResponse;
import yummy.mryummy.Splash.Model.ChangePasswordResponse;
import yummy.mryummy.Splash.Model.CooldrinksResponse;
import yummy.mryummy.Splash.Model.DrinksSelectCartResponse;
import yummy.mryummy.Splash.Model.FavouriteResponse;
import yummy.mryummy.Splash.Model.ForgotResponse;
import yummy.mryummy.Splash.Model.LikeResponse;
import yummy.mryummy.Splash.Model.LoginResponse;
import yummy.mryummy.Splash.Model.OTPResponse;
import yummy.mryummy.Splash.Model.PaymentOrderResponse;
import yummy.mryummy.Splash.Model.ProfileUpdateResponse;
import yummy.mryummy.Splash.Model.RegistrationResponse;
import yummy.mryummy.Splash.Model.RestaurentItemResponse;
import yummy.mryummy.Splash.Model.RestaurentResponse;
import yummy.mryummy.Splash.Model.SearchResponse;

/**
 * Created by acer on 12/16/2017.
 */

public interface MyYummyServices {

    @FormUrlEncoded
    @POST("registration.php?")
    Call<RegistrationResponse> registration(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("login.php?")
    Call<LoginResponse> login(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("otp.php?")
    Call<OTPResponse> otp_verify(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("forgetpassword.php?")
    Call<ForgotResponse> forgotpassword(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("address.php?")
    Call<AddressRespponse> saveaddress(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("changepassword.php?")
    Call<ChangePasswordResponse> changepassword(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("updateprofile.php?")
    Call<ProfileUpdateResponse> updateprofile(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("category.php?")
    Call<RestaurentResponse> restaurentslist(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("items.php?")
    Call<RestaurentItemResponse> restaurentitemslist(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("search.php?")
    Call<SearchResponse> searchlist(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("getfavourites.php?")
    Call<FavouriteResponse> favouritelist(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("managefavourites.php?")
    Call<LikeResponse> like_dislike_action(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("payment.php?")
    Call<PaymentOrderResponse> paymentorder(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("cookdrinks.php")
    Call<CooldrinksResponse> cooldrinks(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("addrinkstocart.php?")
    Call<DrinksSelectCartResponse> cooldrinkscart(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("cart.php?")
    Call<CartItemsResponse> itemscart(@FieldMap HashMap<String, Object> map);



}
