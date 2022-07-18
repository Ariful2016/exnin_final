package com.exnin.onlinelearning.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.exnin.onlinelearning.AppConstants.AppConstants;
import com.exnin.onlinelearning.Models.AllCourse;
import com.exnin.onlinelearning.Models.AllCourseParent;
import com.exnin.onlinelearning.Models.CourseDay;
import com.exnin.onlinelearning.MyApi;
import com.exnin.onlinelearning.MyRetrofit;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {


    Toolbar kidToolbar;
    AppCompatButton enroll_btn;

    CircleImageView course_img, int_img;

    TextView course_title_txt, overviewDescription_txt, kid_subject_txt, kid_subject_bem_txt,
            duration_month_txt, total_class_txt, total_class_time_txt, taka_txt, usd_txt;
    TextView first_installment_txt, second_installment_txt, third_installment_txt, first_details_txt, second_details_txt,third_details_txt;

    Intent intent;

    String img;
    String title, overview, slug, duration, totalClass, classInfo, fee, usdeuro, installment, courseId;

    Dialog dialog;


    TimePickerDialog timePickerDialog;
    TimePicker timePicker;

    List<CourseDay> courseDayList;

    String[] selectDay;

    MyApi myApi;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        myApi = MyRetrofit.getRetrofit().create(MyApi.class);

        intent = getIntent();

        kidToolbar = findViewById(R.id.kidsToolbar);
        enroll_btn = findViewById(R.id.enroll_btn);

        setSupportActionBar(kidToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        courseDayList = new ArrayList<>();

        course_img = findViewById(R.id.course_img);
        course_title_txt = findViewById(R.id.course_title_txt);
        overviewDescription_txt = findViewById(R.id.overviewDescription_txt);
        kid_subject_txt = findViewById(R.id.kid_subject_txt);
        duration_month_txt = findViewById(R.id.duration_month_txt);
        total_class_txt = findViewById(R.id.total_class_txt);
        total_class_time_txt = findViewById(R.id.total_class_time_txt);
        taka_txt = findViewById(R.id.taka_txt);
        usd_txt = findViewById(R.id.usd_txt);
        first_installment_txt = findViewById(R.id.first_installment_txt);
        second_installment_txt = findViewById(R.id.second_installment_txt);
        third_installment_txt = findViewById(R.id.third_installment_txt);

        third_details_txt = findViewById(R.id.third_details_txt);
        first_details_txt = findViewById(R.id.first_details_txt);
        second_details_txt = findViewById(R.id.second_details_txt);


        if (intent.hasExtra("id")) {

            courseId = intent.getStringExtra("id");

            getCourse(courseId);

        }





        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);
        Call<List<CourseDay>> cDay = myApi.getCourseDay();


        timePicker = new TimePicker(this);
        int currentHour = timePicker.getCurrentHour();
        int currentMin = timePicker.getCurrentMinute();

        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);
        builder.setTitle("Select your Day and Time");

        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        AutoCompleteTextView day;
        TextInputEditText time;
        AppCompatButton addCart;
        TextView cancel;
        day = view.findViewById(R.id.select_day);
        time = view.findViewById(R.id.select_time);
        addCart = view.findViewById(R.id.addCart_btn);
        cancel = view.findViewById(R.id.cancel_txt);

        cDay.enqueue(new Callback<List<CourseDay>>() {
            @Override
            public void onResponse(Call<List<CourseDay>> call, Response<List<CourseDay>> response) {
                courseDayList = response.body();
                assert courseDayList != null;
                selectDay = new String[courseDayList.size()];

                for (int i = 0; i < courseDayList.size(); i++) {

                    selectDay[i] = courseDayList.get(i).getName();
                }

                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(CourseDetailsActivity.this, android.R.layout.simple_list_item_1, selectDay);
                day.setAdapter(dayAdapter);

            }

            @Override
            public void onFailure(Call<List<CourseDay>> call, Throwable t) {

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(CourseDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, currentHour, currentMin, true);
                timePickerDialog.show();

            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                int uId = sharedPreferences.getInt("userId", 0);
                String userID = String.valueOf(uId);

                String token = sharedPreferences.getString("token", "");

                Log.i("TAG", "onClick: " + token);

                String day_str = day.getText().toString();
                String time_str = time.getText().toString();

                if (day_str.isEmpty()){
                    ShowError("Please select day");
                }else if (time_str.isEmpty()){
                    ShowError("Please select time");
                }else {

                    if (SharedPrefManager.getInstance(CourseDetailsActivity.this).isLoggedIn()) {

                        myApi.courseEnroll("Bearer " + token, day_str, time_str, userID, courseId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                Log.i("Enroll", "onResponse: " + response);
                                dialog.dismiss();
                                startActivity(new Intent(CourseDetailsActivity.this, CartActivity.class));
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                Log.i("Enroll", "onFailure: " + t.getMessage());
                                dialog.dismiss();

                            }
                        });
                    } else {
                        startActivity(new Intent(CourseDetailsActivity.this, SignInActivity.class));
                    }
                }




            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);

        dialog = builder.create();

        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }

    private void getCourse(String courseId) {

        myApi.getAllCourse().enqueue(new Callback<AllCourseParent>() {
            @Override
            public void onResponse(Call<AllCourseParent> call, Response<AllCourseParent> response) {
                AllCourseParent allCourseParent = response.body();
                List<AllCourse> allCourse = allCourseParent.getCourse();

                Log.i("TAG", "onResponse: " + allCourse);



                Integer cId = Integer.parseInt(courseId);
                for (AllCourse courses : allCourse) {

                    if (cId.equals(courses.getId())) {

                        course_title_txt.setText(courses.getName());
                        Picasso.get().load(AppConstants.course_image_path + courses.getBannerImage()).placeholder(R.drawable.kid_learning_all_courses).into(course_img);
                        overviewDescription_txt.setText(courses.getOverview());
                        kid_subject_txt.setText(courses.getSlug());
                        duration_month_txt.setText("Duration : "+courses.getDuration()+" month");
                        total_class_txt.setText("Total Class : "+courses.getTotalClass());
                        total_class_time_txt.setText("("+courses.getClassInfo()+")");
                        taka_txt.setText("Total Price : "+courses.getCourseFee()+" BDT");
                        usd_txt.setText("("+courses.getUsdeuro()+")");

                        for (int i = 0; i < courses.getInstallments().size(); ++i) {

                            switch (i) {
                                case 0:
                                    first_installment_txt.setVisibility(View.VISIBLE);
                                    first_details_txt.setVisibility(View.VISIBLE);
                                    first_installment_txt.setText("First Instalment : " + courses.getInstallments().get(0).getBdt()+" BDT");
                                    break;
                                case 1:
                                    second_installment_txt.setVisibility(View.VISIBLE);
                                    second_details_txt.setVisibility(View.VISIBLE);
                                    second_installment_txt.setText("Second Instalment : " + courses.getInstallments().get(1).getBdt()+" BDT");
                                    break;
                                case 2:
                                    third_installment_txt.setVisibility(View.VISIBLE);
                                    third_details_txt.setVisibility(View.VISIBLE);
                                    third_installment_txt.setText("Third Instalment : " + courses.getInstallments().get(2).getBdt()+" BDT");
                                    break;


                            }

                           // break;


                        }


                    }


                }


                //    first_installment_txt.setText(allCourse.get(0).getInstallments().get(0).getBdt());


            }

            @Override
            public void onFailure(Call<AllCourseParent> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });


    }


    private void ShowError(String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);

        builder.setIcon(R.drawable.ic_error);
        builder.setTitle("Error");
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}