package com.es.netschool24.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.es.netschool24.Activities.OurCourseActivity;
import com.es.netschool24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CourseOrderFragment extends Fragment {



    public CourseOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_order, container, false);


        return view;
    }
}