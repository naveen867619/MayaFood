package com.mayaf.mayajaalfb.interfaces;

import com.mayaf.mayajaalfb.foodlist.model.ProductInfoModel;

import java.util.List;

/**
 * Created by innoppl on 8/8/16.
 */
public interface UpdateFoodListListener {

    void updateFoodList(List<ProductInfoModel> productInfoModelList);
}
