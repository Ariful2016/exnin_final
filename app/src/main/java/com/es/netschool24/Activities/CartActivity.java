package com.es.netschool24.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.es.netschool24.Adapters.CartAdapter;
import com.es.netschool24.Models.Cart;
import com.es.netschool24.Models.CartCourse;
import com.es.netschool24.Models.CartCourseMain;
import com.es.netschool24.MyApi;
import com.es.netschool24.MyRetrofit;
import com.es.netschool24.R;
import com.es.netschool24.interfaces.CartDeleteListener;
import com.es.netschool24.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartDeleteListener {

    Toolbar toolbar;

    AppCompatButton check_btn;

    String d, t, img, title, fee;
    CircleImageView kids_img;

    List<Cart> cartList;
    RecyclerView cart_recycler;
    List<CartCourse> cartCourseList;
    List<CartCourseMain> cartCourseMainList;

    CartAdapter cartAdapter;

    TextView total_taka;
    MyApi myApi;
    String token;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.enroll_toolbar);
        check_btn = findViewById(R.id.check_btn);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartList = new ArrayList<>();
        cartCourseList = new ArrayList<>();
        cartCourseMainList = new ArrayList<>();

        cart_recycler = findViewById(R.id.cart_recycler);
        total_taka = findViewById(R.id.total_taka);


        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        myApi = MyRetrofit.getRetrofit().create(MyApi.class);


        getAllCart();

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,CartCheckoutActivity.class ));
            }
        });

    }

    private void getAllCart() {
        Call<CartCourseMain> callCart = myApi.getCart("Bearer " + token);
        callCart.enqueue(new Callback<CartCourseMain>() {
            @Override
            public void onResponse(Call<CartCourseMain> call, Response<CartCourseMain> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    cartList = response.body().getCarts();
                    Log.i("cartId", "success " + response.body().getCarts().size());

                    cartAdapter = new CartAdapter(CartActivity.this, cartList, CartActivity.this);
                    // cartAdapter.notifyDataSetChanged();
                    cart_recycler.setAdapter(cartAdapter);

                    String total_price = response.body().getPayTotal().toString();

                    Log.i("cartId", "success " + total_price + " tk");
                    total_taka.setText(total_price + ".00 BDT");


                }
            }

            @Override
            public void onFailure(Call<CartCourseMain> call, Throwable t) {
                Log.i("cartId", "fail " + t.getLocalizedMessage().toString());

            }
        });


    }

    @Override
    public void deleteCart(Cart cart) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);
        Call<ResponseBody> removeCart = myApi.removeCart("Bearer " + token, cart.getId().toString());
        removeCart.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    getAllCart();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("remove", "not_remove: " + t.getLocalizedMessage().toString());
            }
        });


    }
}