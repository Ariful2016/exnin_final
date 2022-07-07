package com.es.netschool24.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.es.netschool24.Adapters.CartAdapter;
import com.es.netschool24.Adapters.CartCheckoutAdapter;
import com.es.netschool24.Models.Cart;
import com.es.netschool24.Models.CartCourseMain;
import com.es.netschool24.Models.LoginResponse;
import com.es.netschool24.Models.StudentProfile;
import com.es.netschool24.Models.User;
import com.es.netschool24.MyApi;
import com.es.netschool24.MyRetrofit;
import com.es.netschool24.R;
import com.es.netschool24.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartCheckoutActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView userName,userMobile,userEmail,userAddress,total_taka;
    RecyclerView cart_checkout_recycler;

    AppCompatButton check_btn;

    List<Cart> cartList;
    CartCheckoutAdapter cartCheckoutAdapter;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        toolbar = findViewById(R.id.cart_checkout_toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartList = new ArrayList<>();

        userName = findViewById(R.id.userName);
        userMobile = findViewById(R.id.userMobile);
        userEmail = findViewById(R.id.userEmail);
        userAddress = findViewById(R.id.userAddress);
        total_taka = findViewById(R.id.total_taka);

        cart_checkout_recycler = findViewById(R.id.cart_checkout_recycler);
        check_btn = findViewById(R.id.check_btn);

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        String userID = String.valueOf(uId);
        String token = sharedPreferences.getString("token", "");

        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);
        Call<StudentProfile> studentProfileCall = myApi.getStudentProfile("Bearer "+token);
        //Call<LoginResponse> getUserCall = myApi.getUser("Bearer "+token);







        Log.i("uId", "userID1: "+ uId);

        if (SharedPrefManager.getInstance(CartCheckoutActivity.this).isLoggedIn()){
            studentProfileCall.enqueue(new Callback<StudentProfile>() {
                @Override
                public void onResponse(Call<StudentProfile> call, Response<StudentProfile> response) {
                    assert response.body() != null;
                    Log.i("uId", "onResponse: "+response.body());
                    if(uId == response.body().getId()){

                        userMobile.setText(response.body().getStudent().getMobile());
                        userAddress.setText(response.body().getStudent().getAddress());
                        userName.setText(response.body().getName());
                        userEmail.setText(response.body().getEmail());
                    }
                }

                @Override
                public void onFailure(Call<StudentProfile> call, Throwable t) {
                    Log.i("uId", "onResponse: "+t.getMessage());
                }
            });



        }


        Call<CartCourseMain> callCart = myApi.getCart("Bearer " + token);
        callCart.enqueue(new Callback<CartCourseMain>() {
            @Override
            public void onResponse(Call<CartCourseMain> call, Response<CartCourseMain> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    cartList = response.body().getCarts();
                    cartCheckoutAdapter = new CartCheckoutAdapter(CartCheckoutActivity.this, cartList);
                    cart_checkout_recycler.setAdapter(cartCheckoutAdapter);

                    String total_price = response.body().getPayTotal().toString();
                    total_taka.setText(total_price + ".00 BDT");


                }
            }

            @Override
            public void onFailure(Call<CartCourseMain> call, Throwable t) {
                Log.i("cartId", "fail " + t.getLocalizedMessage().toString());

            }
        });
















    }
}