package com.mayaf.mayajaalfb.foodlist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mayaf.mayajaalfb.R;
import com.mayaf.mayajaalfb.customviews.customalertdialog.CustomAlertDialogView;
import com.mayaf.mayajaalfb.customviews.customalertdialog.interfaces.CustomAlertDialogInterface;
import com.mayaf.mayajaalfb.foodlist.adapter.FoodAdapter;
import com.mayaf.mayajaalfb.foodlist.enums.FoodListEnum;
import com.mayaf.mayajaalfb.foodlist.model.FoodPostModel;
import com.mayaf.mayajaalfb.interfaces.ItemClickListener;
import com.mayaf.mayajaalfb.interfaces.OnLoadMoreListener;
import com.mayaf.mayajaalfb.interfaces.UpdateFoodListListener;
import com.mayaf.mayajaalfb.foodlist.model.FoodModel;
import com.mayaf.mayajaalfb.foodlist.model.ProductInfoModel;
import com.mayaf.mayajaalfb.utils.Utilities;
import com.mayaf.mayajaalfb.webservice.RetrofitServiceGenerator;
import com.mayaf.mayajaalfb.webservice.interfaces.RestApi;
import com.mayaf.mayajaalfb.webservice.interfaces.WebServiceConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class FoodListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener, View.OnClickListener, UpdateFoodListListener, CustomAlertDialogInterface {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView foodRecyclerView;
    List<ProductInfoModel> productInfoModelArrayList = new ArrayList<>();
    List<ProductInfoModel> tempProductInfoModelArrayList = new ArrayList<>();


    FoodAdapter foodAdapter;
    private int OFFSET = 0;
    ProgressDialog progressDialog;
    String totalCount = "";
    Button confirmButton;
    // String userDetails = "";
    Toolbar toolbar;
    Intent intent;
    CustomAlertDialogView customAlertDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        customAlertDialogView = new CustomAlertDialogView(FoodListActivity.this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //    setSupportActionBar(toolbar);
        //   getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle("Food and Beverages");
        toolbar.setTitleTextColor(Color.WHITE);

        intent = getIntent();
      /*  String name = intent.getStringExtra("name");
        String mobno = intent.getStringExtra("mobno");
        String seat = intent.getStringExtra("seatno");
        String odate = intent.getStringExtra("odate");
        String screen = intent.getStringExtra("sp");
        String showtime = intent.getStringExtra("showtime");
        String otime = intent.getStringExtra("otime");
        String cash = intent.getStringExtra("sp2");

        userDetails = "\n\nName:" + name
                + "\nMobile No:" + mobno
                + "\nSeat No:" + seat
                + "\nScreen No:" + screen
                + "\nOrder date:" + odate
                + "\nShow time:" + showtime
                + "\nOrder Time:" + otime
                + "\nCash:" + cash;*/
        //  + "\n\nToatl Rs:" + totalValue.getText().toString();

        foodRecyclerView = (RecyclerView) findViewById(R.id.foodRecyclerView);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        confirmButton.setOnClickListener(this);
       /* for (int i = 1; i <= 30; i++) {
            FoodModel foodModel = new FoodModel();
            foodModel.setName("item" + " " + i);
            foodModel.setImageUrl("http://yaswebcastvod.edgesuite.net/Stargenie/webcasts/57a0859665e3829dbb7730bb-webcasts-1470137750872.png");
            foodModelArrayList.add(foodModel);
        }*/

        setupRecycler();

        foodAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                OFFSET = OFFSET + 10;
                if (Integer.parseInt(totalCount) > OFFSET) {
                    serverRequest(FoodListEnum.FOOD_LIST.ordinal(), null);
                    //  Toast.makeText(MainActivity.this, "Load More", Toast.LENGTH_SHORT).show();
                }
            }
        });

        serverRequest(FoodListEnum.FOOD_LIST.ordinal(), null);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_2);
    }


    private void serverRequest(int requestCode, JSONObject jsonObject) {

        if (Utilities.getInstance().isInternetAvail(FoodListActivity.this)) {
            if (requestCode == FoodListEnum.FOOD_LIST.ordinal())
                doInBackground(requestCode, null);
            else if (requestCode == FoodListEnum.POST_FOOD_DATA.ordinal()) {
                Map<String, String> params = new HashMap<String, String>();
                if (jsonObject != null)
                    params.put(WebServiceConstants.data, jsonObject.toString());
                doInBackground(requestCode, params);
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(FoodListActivity.this, "" + getString(R.string.check_internet_conncetion), Toast.LENGTH_SHORT).show();
        }

    }

    public void doInBackground(int requestCode, Map<String, String> params) {

        progressDialog.show();
        final RestApi restApi = RetrofitServiceGenerator.createService(RestApi.class);


        if (requestCode == FoodListEnum.FOOD_LIST.ordinal()) {

            // RestApi restApi = RetrofitServiceGenerator.getApi(RestApi.class, accessToken);
            Call<FoodModel> call = restApi.getFoodList(OFFSET);

            call.enqueue(new Callback<FoodModel>() {
                @Override
                public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                    try {
                        int statusCode = response.code();
                        Log.e("statusCode", "onResponse: " + statusCode);

                        if (response.isSuccessful()) { //200-399
                            progressDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.e("isSuccessful", "onResponse: ");
                            FoodModel foodModel = response.body();
                            productInfoModelArrayList = foodModel.getResult().getProductInfo();
                            totalCount = foodModel.getResult().getProduct_count();
                            foodAdapter.addAll(productInfoModelArrayList);
                            addAllList(productInfoModelArrayList);
                            foodAdapter.setLoaded();

                        } else {
                            //400-599
                            progressDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);

                            Converter<ResponseBody, FoodModel> errorConverter = RetrofitServiceGenerator.retrofit.responseBodyConverter(FoodModel.class, new Annotation[0]);
                            FoodModel foodModel = errorConverter.convert(response.errorBody());
                            Log.e("isFailure", "onResponse: " + foodModel.getResult().getMsg());
                            Toast.makeText(FoodListActivity.this, "" + foodModel.getResult().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FoodModel> call, Throwable t) {
                    onFailureHandling(t);
                }
            });
        } else if (requestCode == FoodListEnum.POST_FOOD_DATA.ordinal()) {

            Call<FoodPostModel> call = restApi.postFoodData(params);

            call.enqueue(new Callback<FoodPostModel>() {
                @Override
                public void onResponse(Call<FoodPostModel> call, Response<FoodPostModel> response) {
                    try {
                        int statusCode = response.code();
                        Log.e("statusCode", "onResponse: " + statusCode);

                        if (response.isSuccessful()) { //200-399
                            progressDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.e("isSuccessful", "onResponse: ");
                            FoodPostModel foodPostModel = response.body();

                            Utilities.getInstance().showSingleButtonAlert(customAlertDialogView, FoodListActivity.this, FoodListActivity.this, foodPostModel.getResult().getMsg(), getString(R.string.ok));
                            //  Utilities.getInstance().alertMessage(foodPostModel.getResult().getMsg(), FoodListActivity.this);
                            customAlertDialogView.displaySingleDoubleBtnAlert(FoodListActivity.this, FoodListActivity.this, 1,
                                    foodPostModel.getResult().getMsg(),
                                    getString(R.string.ok), "");
                        } else {
                            //400-599
                            progressDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);

                            Converter<ResponseBody, FoodPostModel> errorConverter = RetrofitServiceGenerator.retrofit.responseBodyConverter(FoodModel.class, new Annotation[0]);
                            FoodPostModel foodModel = errorConverter.convert(response.errorBody());
                            Log.e("isFailure", "onResponse: " + foodModel.getResult().getMsg());
                            // Utilities.getInstance().alertMessage(foodModel.getResult().getMsg(), FoodListActivity.this);
                            Utilities.getInstance().showSingleButtonAlert(customAlertDialogView, FoodListActivity.this, FoodListActivity.this, foodModel.getResult().getMsg(), getString(R.string.ok));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FoodPostModel> call, Throwable t) {
                    onFailureHandling(t);
                }
            });

        }
    }

    private void onFailureHandling(Throwable throwable) {

        progressDialog.dismiss();
        mSwipeRefreshLayout.setRefreshing(false);

        if (throwable instanceof SocketTimeoutException)
            Toast.makeText(FoodListActivity.this, "" + getString(R.string.oops_time_out), Toast.LENGTH_SHORT).show();
        else if (throwable instanceof IOException)
            Toast.makeText(FoodListActivity.this, "" + getString(R.string.check_internet_conncetion), Toast.LENGTH_SHORT).show();


    }

    private void setupRecycler() {
        try {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            foodRecyclerView.setHasFixedSize(true);

            // use a linear layout manager since the cards are vertically scrollable
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            foodRecyclerView.setLayoutManager(layoutManager);

            // create an empty adapter and add it to the recycler view
            foodAdapter = new FoodAdapter(FoodListActivity.this, productInfoModelArrayList, FoodListActivity.this, foodRecyclerView, FoodListActivity.this);
            foodRecyclerView.setAdapter(foodAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        //   Toast.makeText(MainActivity.this, "position : " + position, Toast.LENGTH_SHORT).show();
    }

    public void addAllList(List<ProductInfoModel> list) {
        tempProductInfoModelArrayList.addAll(list);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.confirmButton) {

            try {
                Log.e("tect", "onClick: " + tempProductInfoModelArrayList);

                Iterator<ProductInfoModel> iterator = tempProductInfoModelArrayList.iterator();
                while (iterator.hasNext())
                    if (iterator.next().getFoodCount() == 0)
                        iterator.remove();

                Log.e("Size", "onClick: " + tempProductInfoModelArrayList.size());
                String result = "";
                float totalPrice = 0;

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                jsonObject.put("name", intent.getStringExtra("name"));
                jsonObject.put("mobile_no", intent.getStringExtra("mobno"));
                jsonObject.put("screen_type", intent.getStringExtra("sp"));
                jsonObject.put("seat_number", intent.getStringExtra("seatno"));
                jsonObject.put("show_time", intent.getStringExtra("showtime"));
                jsonObject.put("order_date", intent.getStringExtra("odate"));
                jsonObject.put("order_time", intent.getStringExtra("otime"));
                jsonObject.put("cash_type", intent.getStringExtra("sp2"));

                if (tempProductInfoModelArrayList.size() > 0) {
                    for (int i = 0; i < tempProductInfoModelArrayList.size(); i++) {
                        result = result + "\n" + tempProductInfoModelArrayList.get(i).getPname() + " " + ":" + " " + tempProductInfoModelArrayList.get(i).getFoodCount();
                        Log.e("individual", "Price: " + Float.parseFloat(String.valueOf(tempProductInfoModelArrayList.get(i).getFoodCount())) * Float.parseFloat(tempProductInfoModelArrayList.get(i).getPrice()));

                        JSONObject foodJsonObject = new JSONObject();
                        foodJsonObject.put("foodId", String.valueOf(tempProductInfoModelArrayList.get(i).getPid()));
                        foodJsonObject.put("foodName", String.valueOf(tempProductInfoModelArrayList.get(i).getPname()));
                        foodJsonObject.put("count", String.valueOf(tempProductInfoModelArrayList.get(i).getFoodCount()));
                        foodJsonObject.put("price", String.valueOf(Float.parseFloat(tempProductInfoModelArrayList.get(i).getPrice())));

                        jsonArray.put(foodJsonObject);
                        jsonObject.put("foodArray", jsonArray);

                        totalPrice = totalPrice + (Float.parseFloat(String.valueOf(tempProductInfoModelArrayList.get(i).getFoodCount())) * Float.parseFloat(tempProductInfoModelArrayList.get(i).getPrice()));
                    }
                } else
                    Utilities.getInstance().showSingleButtonAlert(customAlertDialogView, FoodListActivity.this, FoodListActivity.this, getString(R.string.choose_food), getString(R.string.ok));
                // Utilities.getInstance().alertMessage(getString(R.string.choose_food), FoodListActivity.this);

                Log.e("totalPrice", "onClick: " + totalPrice);
                if (totalPrice != 0)
                    sendUserAndFoodData(totalPrice, jsonObject);

                Log.e("jsonObject", "onClick: " + jsonObject.toString());


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateFoodList(List<ProductInfoModel> productInfoModelList) {

        tempProductInfoModelArrayList = new ArrayList<ProductInfoModel>(productInfoModelList);

        // tempProductInfoModelArrayList = productInfoModelList;
    }

    private void sendUserAndFoodData(final float totalPrice, final JSONObject jsonObject) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.total_price) + " - " + getString(R.string.rupee_symbol) + ":" + " " + totalPrice)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        serverRequest(FoodListEnum.POST_FOOD_DATA.ordinal(), jsonObject);

                        //do things
                        /*Toast.makeText(FoodListActivity.this, "" + getString(R.string.choose_gmail), Toast.LENGTH_SHORT).show();
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mayafoodcourt@gmail.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Food details");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, userDetails + "\n" + "TotalPrice" + ":" + totalPrice + "\n" + result);

                        emailIntent.setType("plain/text");
                        /// use below 2 commented lines if need to use BCC an CC feature in email
                        //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
                        //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
                        ////use below 3 commented lines if need to send attachment
                        //need this to prompts email client only
                        emailIntent.setType("message/rfc822");
                        startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));*/
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onRefresh() {
        // Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);*/

        //  if (Utilities.getInstance().isInternetAvail(FoodListActivity.this))
        serverRequest(FoodListEnum.FOOD_LIST.ordinal(), null);

    }

    @Override
    public void dataSelection(int selectedPos) {

    }

    @Override
    public void alertPositiveBtn(int statusCode) {
        if (statusCode == 1) {
            //   Toast.makeText(FoodListActivity.this, "Post Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void alertNegativeBtn(int statusCode) {

    }

    @Override
    public void onReConnect() {

    }
}
