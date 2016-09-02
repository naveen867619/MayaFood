package com.mayaf.mayajaalfb.foodlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by
 */
public class FoodModel {

    @SerializedName("result")
    @Expose
    private ResultModel result;

    /**
     * @return The result
     */
    public ResultModel getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(ResultModel result) {
        this.result = result;
    }
}
