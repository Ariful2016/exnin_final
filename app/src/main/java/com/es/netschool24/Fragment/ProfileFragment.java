package com.es.netschool24.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.es.netschool24.AppConstants.AppConstants;
import com.es.netschool24.Models.StudentProfile;
import com.es.netschool24.MyApi;
import com.es.netschool24.MyRetrofit;
import com.es.netschool24.R;
import com.es.netschool24.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    ImageView cover_img,edit_pen;
    CircleImageView profile_img;
    TextView name,email,phone,dath_of_birth,father_name,mother_name,
            address,whatsapp,nationality,guardian_name,
            guardian_phone,guardian_relation,gender;



    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edit_pen = view.findViewById(R.id.edit_pen);
        profile_img = view.findViewById(R.id.profile_img);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        dath_of_birth = view.findViewById(R.id.dath_of_birth);
        father_name = view.findViewById(R.id.father_name);
        guardian_name = view.findViewById(R.id.guardian_name);
        gender = view.findViewById(R.id.gender);
        address = view.findViewById(R.id.address);
        nationality = view.findViewById(R.id.nationality);
        guardian_phone = view.findViewById(R.id.guardian_phone);



        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        String userID = String.valueOf(uId);
        String token = sharedPreferences.getString("token", "");

        Call<StudentProfile> studentProfileCall = myApi.getStudentProfile("Bearer "+token);

        studentProfileCall.enqueue(new Callback<StudentProfile>() {
            @Override
            public void onResponse(Call<StudentProfile> call, Response<StudentProfile> response) {
                assert response.body() != null;
                Log.i("uId", "onResponse: "+response.body());
                if(uId == response.body().getId()){

                    /*if(response.body().getStudent().getProfilePhoto().equals(true)){
                        profile_img.setImageResource(R.drawable.profile_logo);
                    }else {
                        Picasso.get().load(AppConstants.student_profile_image_path +response.body().getStudent().getProfilePhoto()).into(profile_img);
                    }*/
                    name.setText("Name : "+response.body().getName());
                    email.setText("Email : "+response.body().getEmail());
                    phone.setText("Mobile : "+ response.body().getStudent().getMobile());
                    dath_of_birth.setText("Date of Birth : "+ response.body().getStudent().getBirthday());
                    gender.setText("Gender : "+ response.body().getStudent().getGender());
                    father_name.setText("Father Name : "+ response.body().getStudent().getFathername());
                    guardian_name.setText("Guardian Name : "+ response.body().getStudent().getGuardianname());
                    guardian_phone.setText("Guardian Mobile No. : "+ response.body().getStudent().getGnumber());
                    nationality.setText("Nationality : "+ response.body().getStudent().getNationality());
                    address.setText("Address : "+ response.body().getStudent().getAddress());

                }
            }

            @Override
            public void onFailure(Call<StudentProfile> call, Throwable t) {
                Log.i("uId", "onResponse: "+t.getMessage());
            }
        });


        return view;
    }
}