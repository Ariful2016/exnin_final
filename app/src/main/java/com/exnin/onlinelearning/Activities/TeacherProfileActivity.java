package com.exnin.onlinelearning.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exnin.onlinelearning.Adapters.TeacherProfileFragmentAdapter;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;
import com.google.android.material.tabs.TabLayout;

public class TeacherProfileActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TeacherProfileFragmentAdapter teacherProfileFragmentAdapter;
    FragmentManager fragmentManager;
    ImageView back;

    TextView not_register_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        not_register_txt = findViewById(R.id.not_register_txt);
        back = findViewById(R.id.back);


        fragmentManager =getSupportFragmentManager();



        if (SharedPrefManager.getInstance(TeacherProfileActivity.this).isLoggedIn()){
            teacherProfileFragmentAdapter = new TeacherProfileFragmentAdapter(fragmentManager);
            viewPager.setAdapter(teacherProfileFragmentAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }else {
            not_register_txt.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherProfileActivity.this,DashboardActivity.class);
                startActivity(intent);

            }
        });
    }
}