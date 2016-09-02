package com.mayaf.mayajaalfb.foodlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 1/9/16.
 */
public class FoodPostModel {

    @SerializedName("result")
    @Expose
    private ResultFoodPostModel result;

    /**
     * @return The result
     */
    public ResultFoodPostModel getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(ResultFoodPostModel result) {
        this.result = result;
    }

}
