package com.mayaf.mayajaalfb.foodlist.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayaf.mayajaalfb.R;
import com.mayaf.mayajaalfb.interfaces.ItemClickListener;

/**
 * Created by
 */
public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ImageView foodImage;
    public final TextView foodTitle, foodCountTextView, priceTextview, total_price_textview, total_textview;
    ItemClickListener itemClickListener;
    public final RelativeLayout foodSubContainerLayout;

    public FoodViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.itemClickListener = itemClickListener;
        foodImage = (ImageView) view.findViewById(R.id.food_image);
        foodTitle = (TextView) view.findViewById(R.id.food_textview);
        priceTextview = (TextView) view.findViewById(R.id.price_textview);
        total_price_textview = (TextView) view.findViewById(R.id.total_price_textview);
        total_textview = (TextView) view.findViewById(R.id.total_textview);
        foodCountTextView = (TextView) view.findViewById(R.id.food_item_quantity_tv);
        foodSubContainerLayout = (RelativeLayout) view.findViewById(R.id.food_item_quantity_sub_container);
        foodSubContainerLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnItemClick(v, getAdapterPosition());
    }
}
