package com.exnin.onlinelearning.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.exnin.onlinelearning.Models.CartCourseMain;
import com.exnin.onlinelearning.MyApi;
import com.exnin.onlinelearning.MyRetrofit;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements SSLCTransactionResponseListener {

    Toolbar payment_toolbar;


    TextView amount_txt;
    ConstraintLayout payment_layout;
    String total_price;
    double amount;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payment_toolbar = findViewById(R.id.payment_toolbar);
        amount_txt = findViewById(R.id.amount_txt);
        payment_layout = findViewById(R.id.payment_layout);

        setSupportActionBar(payment_toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        String token = sharedPreferences.getString("token", "");

        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);
        Call<CartCourseMain> callCart = myApi.getCart("Bearer " + token);
        callCart.enqueue(new Callback<CartCourseMain>() {
            @Override
            public void onResponse(Call<CartCourseMain> call, Response<CartCourseMain> response) {
                if (response.isSuccessful()) {
                    total_price = response.body().getPayTotal().toString();
                    //amount_txt.setText(total_price + ".00 BDT is paid");

                    amount = Double.parseDouble(total_price);
                }
            }

            @Override
            public void onFailure(Call<CartCourseMain> call, Throwable t) {
                Log.i("cartId", "fail " + t.getLocalizedMessage().toString());

            }
        });

        // store id = creat613377970f2ed
        // store pass = creat613377970f2ed@ssl

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("creat613377970f2ed", "creat613377970f2ed@ssl", amount, SSLCCurrencyType.BDT, "123456789098765", "yourProductType", SSLCSdkType.TESTBOX);


        IntegrateSSLCommerz
                .getInstance(PaymentActivity.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .buildApiCall(this);





    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {

        amount_txt.setText(sslcTransactionInfoModel.getAmount()+".00 BDT is paid");
        payment_layout.setVisibility(View.VISIBLE);
        Log.i("payment", "transactionSuccess: "+sslcTransactionInfoModel.getAmount() );
    }

    @Override
    public void transactionFail(String s) {
        Log.i("payment", "fail: "+ s);
    }

    @Override
    public void merchantValidationError(String s) {
        Log.i("payment", "error: "+ s);
    }
}