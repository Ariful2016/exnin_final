package com.exnin.onlinelearning.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exnin.onlinelearning.AppConstants.AppConstants;
import com.exnin.onlinelearning.Models.TeacherProfile;
import com.exnin.onlinelearning.MyApi;
import com.exnin.onlinelearning.MyRetrofit;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherProfileFragment extends Fragment {

    CircleImageView profile_img;
    ImageView edit_pen,ind_photo,certificate_photo;
    TextView name,email,phone,dath_of_birth,gender,
            courses,father_name,address,parmanent_address,
            nationality,education,university;


    public TeacherProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        profile_img = view.findViewById(R.id.profile_img);
        edit_pen = view.findViewById(R.id.edit_pen);
        ind_photo = view.findViewById(R.id.ind_photo);
        certificate_photo = view.findViewById(R.id.certificate_photo);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        dath_of_birth = view.findViewById(R.id.dath_of_birth);
        gender = view.findViewById(R.id.gender);
        courses = view.findViewById(R.id.courses);
        father_name = view.findViewById(R.id.father_name);
        address = view.findViewById(R.id.address);
        parmanent_address = view.findViewById(R.id.parmanent_address);
        nationality = view.findViewById(R.id.nationality);
        education = view.findViewById(R.id.education);
        university = view.findViewById(R.id.university);


        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        String userID = String.valueOf(uId);
        String token = sharedPreferences.getString("token", "");

        Call<TeacherProfile> teacherProfileCall = myApi.getTeacherProfile("Bearer "+token);

        teacherProfileCall.enqueue(new Callback<TeacherProfile>() {
            @Override
            public void onResponse(Call<TeacherProfile> call, Response<TeacherProfile> response) {

                if(uId == response.body().getId()){
                    //Log.i("tprofile", "onResponse: "+ response.body().getName());
                    name.setText("Name : "+response.body().getName());
                    email.setText("Email : "+response.body().getEmail());
                    phone.setText("Mobile : "+response.body().getTeacher().getMobile());
                    dath_of_birth.setText("Date of Birth : "+response.body().getTeacher().getBirthday().substring(0,10));
                    gender.setText("Gender : "+response.body().getTeacher().getGender());
                    courses.setText("Courses : "+response.body().getTeacher().getCourses());
                    father_name.setText("Father Name : "+response.body().getTeacher().getFatherName());
                    address.setText("Address : "+response.body().getTeacher().getAddress());
                    parmanent_address.setText("Permanent Address : "+response.body().getTeacher().getParmanetAddress());
                    nationality.setText("Nationality : "+response.body().getTeacher().getNational());
                    education.setText("Education : "+response.body().getTeacher().getTeachereducations());
                    university.setText("Institution : "+response.body().getTeacher().getUniversity());

                    if(!response.body().getTeacher().getPhoto().isEmpty()){
                        Picasso.get().load(AppConstants.teacher_profile_image_path +response.body().getTeacher().getPhoto()).into(profile_img);
                    }else {
                        profile_img.setImageResource(R.drawable.profile);
                    }

                    Picasso.get().load(AppConstants.teacher_nid_image_path +response.body().getTeacher().getNid()).into(ind_photo);

                    Picasso.get().load(AppConstants.teacher_certificate_image_path + response.body().getTeacher().getCertificate()).into(certificate_photo);

                }

            }

            @Override
            public void onFailure(Call<TeacherProfile> call, Throwable t) {
                Log.i("tprofile", "onFailure: "+ t.getMessage());
            }
        });

        return view;
    }
}