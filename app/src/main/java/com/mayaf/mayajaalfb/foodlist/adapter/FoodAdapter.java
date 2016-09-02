package com.mayaf.mayajaalfb.foodlist.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mayaf.mayajaalfb.R;
import com.mayaf.mayajaalfb.interfaces.ItemClickListener;
import com.mayaf.mayajaalfb.interfaces.OnLoadMoreListener;
import com.mayaf.mayajaalfb.interfaces.UpdateFoodListListener;
import com.mayaf.mayajaalfb.foodlist.model.ProductInfoModel;
import com.mayaf.mayajaalfb.foodlist.viewholder.FoodViewHolder;
import java.util.List;

/**
 * Created by
 */
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductInfoModel> productInfoModelArrayList;
    private final Context context;
    private ItemClickListener itemClickListener;
    private UpdateFoodListListener updateFoodListListener;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    private CharSequence[] charSequenceItems = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    AlertDialog.Builder alertDialogBuilder;
    private int arrayListPosition = 0;
    FoodViewHolder foodViewHolder;

    public FoodAdapter(Context context, List<ProductInfoModel> productInfoModelArrayList, ItemClickListener itemClickListener, RecyclerView recyclerView, UpdateFoodListListener updateFoodListListener) {
        this.context = context;
        this.productInfoModelArrayList = productInfoModelArrayList;
        this.itemClickListener = itemClickListener;
        this.updateFoodListListener = updateFoodListListener;
        alertDialogBuilder = new AlertDialog.Builder(context);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading && totalItemCount - 1 <= (lastVisibleItem)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder;

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.food_list_item, viewGroup, false);
        viewHolder = new FoodViewHolder(itemView, itemClickListener);
        viewGroup.setTag(viewHolder);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ProductInfoModel productInfoModel = productInfoModelArrayList.get(position);
        foodViewHolder = (FoodViewHolder) holder;

        if (!TextUtils.isEmpty(productInfoModel.getProductImage()))
            Glide.with(context)
                    .load(productInfoModel.getProductImage())
                    .asBitmap()
                    .placeholder(R.drawable.logo) // can also be a drawable
                    .error(R.drawable.logo) // will be displayed if the image cannot be loaded
                    .centerCrop()
                    .into(foodViewHolder.foodImage);

        foodViewHolder.foodTitle.setText(productInfoModel.getPname());
        foodViewHolder.priceTextview.setText(context.getString(R.string.rupee_symbol) + ":" + " " + productInfoModel.getPrice());

        if (productInfoModel.getParticularFoodTotal() == 0.0) {
            foodViewHolder.total_price_textview.setVisibility(View.GONE);
            foodViewHolder.total_textview.setVisibility(View.GONE);
        } else {
            foodViewHolder.total_price_textview.setVisibility(View.VISIBLE);
            foodViewHolder.total_textview.setVisibility(View.VISIBLE);
            foodViewHolder.total_price_textview.setText(context.getString(R.string.rupee_symbol) + ":" + " " + productInfoModel.getParticularFoodTotal());
        }

        if (productInfoModel.getFoodCount() > 0)
            foodViewHolder.foodSubContainerLayout.setBackground(context.getResources().getDrawable(R.drawable.lit_one_blue_curved_bg));
        else
            foodViewHolder.foodSubContainerLayout.setBackground(context.getResources().getDrawable(R.drawable.lit_one_curved_bg));

        foodViewHolder.foodCountTextView.setText(String.valueOf(productInfoModel.getFoodCount()));

        alertDialogBuilder.setTitle(context.getResources().getString(R.string.choose_quantity));
        alertDialogBuilder.setSingleChoiceItems(charSequenceItems, 0, listener);

        foodViewHolder.foodSubContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  foodViewHolder = (FoodViewHolder) view.getTag();
                arrayListPosition = position;
                alertDialogBuilder.setSingleChoiceItems(charSequenceItems, productInfoModelArrayList.get(arrayListPosition).getFoodCount(), listener);
                alertDialogBuilder.show();
            }
        });
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return productInfoModelArrayList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addAll(List<ProductInfoModel> list) {
        productInfoModelArrayList.addAll(list);
        notifyDataSetChanged();
    }

    private final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int position) {
            dialogInterface.dismiss();

            int selectedPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
            alertDialogBuilder.setSingleChoiceItems(charSequenceItems, selectedPosition, listener);
            productInfoModelArrayList.get(arrayListPosition).setFoodCount(selectedPosition);
            productInfoModelArrayList.get(arrayListPosition).setParticularFoodTotal(Float.parseFloat(String.valueOf(productInfoModelArrayList.get(arrayListPosition).getFoodCount())) * Float.parseFloat(productInfoModelArrayList.get(arrayListPosition).getPrice()));
            notifyItemChanged(arrayListPosition);
            updateFoodListListener.updateFoodList(productInfoModelArrayList);
        }
    };

 /*   private String particularFoodTotalPrice(int foodCount, ){


    }*/

}
