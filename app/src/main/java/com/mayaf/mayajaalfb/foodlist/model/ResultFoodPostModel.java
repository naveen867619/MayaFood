package com.mayaf.mayajaalfb.foodlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 1/9/16.
 */
public class ResultFoodPostModel {
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
