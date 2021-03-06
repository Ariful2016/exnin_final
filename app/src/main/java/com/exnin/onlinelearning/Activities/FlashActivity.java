package com.exnin.onlinelearning.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.exnin.onlinelearning.R;

public class FlashActivity extends AppCompatActivity {

    ImageView flash_img;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        flash_img = findViewById(R.id.flash_img);

        countDownTimer = new CountDownTimer(300,200) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                startActivity(new Intent(FlashActivity.this, DashboardActivity.class));
                finish();

            }
        }.start();
    }
}