package com.exnin.onlinelearning.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.exnin.onlinelearning.AppConstants.AppConstants;
import com.exnin.onlinelearning.Fragment.HomeFragment;
import com.exnin.onlinelearning.Fragment.MessageFragment;
import com.exnin.onlinelearning.Models.Options;
import com.exnin.onlinelearning.Models.StudentProfile;
import com.exnin.onlinelearning.Models.TeacherProfile;
import com.exnin.onlinelearning.MyApi;
import com.exnin.onlinelearning.MyRetrofit;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {


    Toolbar toolbar;

    CircleImageView profile_image;
    ImageView logo,top_logo;

    TextView profileName, profileEmail, aboutUs, yourProfile, course, studentRegistration,teacherRegistration,freeLearning,visitWebsite, payment, notification, complain, contact;


    FragmentManager fragmentManager;

    List<Options> optionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // find toolbar & setup


        toolbar = findViewById(R.id.toolbar);
        // find all text view
        profile_image = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profileName);
        aboutUs = findViewById(R.id.aboutUs);
        yourProfile = findViewById(R.id.yourProfile);
        course = findViewById(R.id.course);
        studentRegistration = findViewById(R.id.studentRegistration);
        teacherRegistration = findViewById(R.id.teacherRegistration);
        freeLearning = findViewById(R.id.freeLearning);
        visitWebsite = findViewById(R.id.visitWebsite);
        contact = findViewById(R.id.contact);
        logo = findViewById(R.id.logo);
        top_logo = findViewById(R.id.top_logo);

        optionsList = new ArrayList<>();

        MyApi myApi = MyRetrofit.getRetrofit().create(MyApi.class);
        Call<List<Options>> options = myApi.getOptions();

        options.enqueue(new Callback<List<Options>>() {
            @Override
            public void onResponse(Call<List<Options>> call, Response<List<Options>> response) {
                optionsList = response.body();
                assert optionsList != null;
                if (optionsList.size()>0){
                    for (Options i : optionsList){
                        Log.i("Logo", "onResponse: "+ AppConstants.logo_image_path+i.getLogo()+ " "+AppConstants.logo_icon_image_path+i.getLogoIcon());

                        toolbar.setLogoDescription(AppConstants.logo_image_path+i.getLogo());

                        //Glide.with(DashboardActivity.this).load(AppConstants.logo_image_path+i.getLogo()).into(top_logo);
                        Picasso.get().load(AppConstants.logo_image_path+i.getLogo()).into(top_logo);

                        //Glide.with(DashboardActivity.this).load(AppConstants.logo_icon_image_path+i.getLogoIcon()).into(logo);
                        Picasso.get().load(AppConstants.logo_icon_image_path+i.getLogoIcon()).into(logo);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Options>> call, Throwable t) {

            }
        });

        setSupportActionBar(toolbar);


        // initialise fragmentmanager
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.container, new HomeFragment(), null).commit();
        }


        DuoDrawerLayout drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);


        drawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hamburger, getApplicationContext().getTheme());
        drawerToggle.setHomeAsUpIndicator(drawable);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent whatsapp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com/"));
                //startActivity(whatsapp);
                startActivity(new Intent(DashboardActivity.this,DashboardActivity.class));

            }
        });

        if (SharedPrefManager.getInstance(DashboardActivity.this).isLoggedIn()){
            profileName.setText(SignInActivity.userName);

        }else {
            profileName.setText("Your Name");
        }




        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int uId = sharedPreferences.getInt("userId", 0);
        //String userID = String.valueOf(uId);
        String token = sharedPreferences.getString("token", "");

        if (SharedPrefManager.getInstance(DashboardActivity.this).isLoggedIn()){
            if(SignInActivity.role.equals("student")){
                Call<StudentProfile> studentProfileCall = myApi.getStudentProfile("Bearer "+token);

                studentProfileCall.enqueue(new Callback<StudentProfile>() {
                    @Override
                    public void onResponse(Call<StudentProfile> call, Response<StudentProfile> response) {
                        assert response.body() != null;
                        Log.i("uId", "onResponse: "+response.body());
                        if(uId == response.body().getId()){
                            if(response.body().getStudent().getProfilePhoto() == null){
                                profile_image.setImageResource(R.drawable.profile);
                            }else {
                                Picasso.get().load(AppConstants.student_profile_image_path +response.body().getStudent().getProfilePhoto()).into(profile_image);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentProfile> call, Throwable t) {
                        Log.i("uId", "onResponse: "+t.getMessage());
                    }
                });
            }else if(SignInActivity.role.equals("teacher")){
                Call<TeacherProfile> teacherProfileCall = myApi.getTeacherProfile("Bearer "+token);

                teacherProfileCall.enqueue(new Callback<TeacherProfile>() {
                    @Override
                    public void onResponse(Call<TeacherProfile> call, Response<TeacherProfile> response) {

                        if(uId == response.body().getId()){
                            if(!response.body().getTeacher().getPhoto().isEmpty()){
                                Picasso.get().load(AppConstants.teacher_profile_image_path +response.body().getTeacher().getPhoto()).into(profile_image);
                            }else {
                                profile_image.setImageResource(R.drawable.profile);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<TeacherProfile> call, Throwable t) {
                        Log.i("tprofile", "onFailure: "+ t.getMessage());
                    }
                });
            }
        }else {
            profile_image.setImageResource(R.drawable.profile);
        }





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentManager.beginTransaction().replace(R.id.container, new HomeFragment(), null).commit();
                        break;

                    case R.id.share:
                        ShareCompat.IntentBuilder
                                .from(DashboardActivity.this)
                                .setType("text/plain")
                                .setChooserTitle("Share URL")
                                .setText("Hey check out my app at :  https://play.google.com/store/apps/details?id="+getPackageName())
                                .startChooser();
                        break;

                    case R.id.whatsapp:
                        break;

                    case R.id.message:
                        fragmentManager.beginTransaction().replace(R.id.container, new MessageFragment(), null).commit();
                        break;

                    case R.id.profile:

                        if (SharedPrefManager.getInstance(DashboardActivity.this).isLoggedIn()){
                                if(SignInActivity.role.equals("student")){
                                    Intent intent = new Intent(DashboardActivity.this, YourProfileActivity.class);
                                    startActivity(intent);
                                }else if(SignInActivity.role.equals("teacher")){
                                    Intent intent = new Intent(DashboardActivity.this, TeacherProfileActivity.class);
                                    startActivity(intent);
                                }
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }

                       // Log.i("onN", "student: "+SignInActivity.role.equals("student"));
                       // Log.i("onN", "teacher: "+SignInActivity.role.equals("teacher"));

                        break;

                }


                return true;
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        yourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, OurCourseActivity.class);
                startActivity(intent);
            }
        });

        studentRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, StudentRegisterActivity.class);
                startActivity(intent);
            }
        });

        teacherRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, TeacherRegisterActivity.class);
                startActivity(intent);
            }
        });

        freeLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, JoinFreeLearningActivity.class);
                startActivity(intent);
            }
        });






        visitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        UserPermission();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu,menu);

        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.privacy_policy){

            //Toast.makeText(DashboardActivity.this, "Privacy and Policy Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, PrivacyPolicyActivity.class));

        }
        if(item.getItemId() == R.id.changePassword){

            Toast.makeText(DashboardActivity.this, "Change Password Clicked", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.logout){
            SharedPrefManager.getInstance(DashboardActivity.this).LoggedOut();
            startActivity(new Intent(DashboardActivity.this, SignInActivity.class));
            finish();
        }



        return super.onOptionsItemSelected(item);
    }

    private void UserPermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)

                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

    }


}