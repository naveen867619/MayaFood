package com.mayaf.mayajaalfb.foodlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 4/8/16.
 */
public class ProductInfoModel {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("mname")
    @Expose
    private String mname;
    @SerializedName("weight_name")
    @Expose
    private String weightName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("product_catalog")
    @Expose
    private String productCatalog;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_color")
    @Expose
    private String productColor;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("price")
    @Expose
    private String price;

    private int foodCount = 0;
    private float particularFoodTotal = 0;

    public float getParticularFoodTotal() {
        return particularFoodTotal;
    }

    public void setParticularFoodTotal(float particularFoodTotal) {
        this.particularFoodTotal = particularFoodTotal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * @return The pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid The pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return The pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname The pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return The pos
     */
    public String getPos() {
        return pos;
    }

    /**
     * @param pos The pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * @return The mname
     */
    public String getMname() {
        return mname;
    }

    /**
     * @param mname The mname
     */
    public void setMname(String mname) {
        this.mname = mname;
    }

    /**
     * @return The weightName
     */
    public String getWeightName() {
        return weightName;
    }

    /**
     * @param weightName The weight_name
     */
    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The productCatalog
     */
    public String getProductCatalog() {
        return productCatalog;
    }

    /**
     * @param productCatalog The product_catalog
     */
    public void setProductCatalog(String productCatalog) {
        this.productCatalog = productCatalog;
    }

    /**
     * @return The productImage
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * @param productImage The product_image
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * @return The productColor
     */
    public String getProductColor() {
        return productColor;
    }

    /**
     * @param productColor The product_color
     */
    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
