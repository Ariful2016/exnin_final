package com.exnin.onlinelearning.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exnin.onlinelearning.Adapters.OrderAdapter;
import com.exnin.onlinelearning.Models.CourseOrder;
import com.exnin.onlinelearning.MyApi;
import com.exnin.onlinelearning.MyRetrofit;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseOrderFragment extends Fragment {

    RecyclerView recycler;
    OrderAdapter orderAdapter;

    List<CourseOrder> courseOrderList;

    public CourseOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_order, container, false);

        recycler = view.findViewById(R.id.recycler);

        courseOrderList = new ArrayList<>();

        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        String userID = String.valueOf(uId);
        String token = sharedPreferences.getString("token", "");

        Log.i("order", "onResponse: " + uId);

        Call<List<CourseOrder>> orderCall = myApi.getCourseOrder("Bearer "+token);

        orderCall.enqueue(new Callback<List<CourseOrder>>() {
            @Override
            public void onResponse(Call<List<CourseOrder>> call, Response<List<CourseOrder>> response) {

                courseOrderList = response.body();
                Log.i("order", "onResponse: " + courseOrderList.size());
                for (CourseOrder  i : courseOrderList){
                    Log.i("order", "onResponse: " + i.getCourseOrderInfo().getName());
                }
                orderAdapter = new OrderAdapter(getContext(),courseOrderList);
                recycler.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<CourseOrder>> call, Throwable t) {

            }
        });

        return view;
    }
}