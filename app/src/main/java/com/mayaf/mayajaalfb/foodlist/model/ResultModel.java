package com.mayaf.mayajaalfb.foodlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mayaf.mayajaalfb.foodlist.model.ProductInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 4/8/16.
 */
public class ResultModel {

    @SerializedName("product_info")
    @Expose
    private List<ProductInfoModel> productInfo = new ArrayList<>();

    @SerializedName("product_count")
    @Expose
    private String product_count;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    /**
     * @return The productInfo
     */
    public List<ProductInfoModel> getProductInfo() {
        return productInfo;
    }

    /**
     * @param productInfo The product_info
     */
    public void setProductInfo(List<ProductInfoModel> productInfo) {
        this.productInfo = productInfo;
    }


}
