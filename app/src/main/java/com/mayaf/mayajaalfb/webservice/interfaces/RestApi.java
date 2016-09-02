package com.mayaf.mayajaalfb.webservice.interfaces;

import com.mayaf.mayajaalfb.foodlist.model.FoodModel;
import com.mayaf.mayajaalfb.foodlist.model.FoodPostModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {

    @GET("api/webservice/productinfo_list/{offset}")
    Call<FoodModel> getFoodList(@Path("offset") int offset);

    @POST("api/index.php/webservice/customer_order")
    @FormUrlEncoded
    Call<FoodPostModel> postFoodData(@FieldMap Map<String,String> params);

}
