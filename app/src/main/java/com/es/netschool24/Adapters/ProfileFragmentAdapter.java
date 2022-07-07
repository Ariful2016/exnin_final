package com.es.netschool24.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.es.netschool24.Fragment.CourseOrderFragment;
import com.es.netschool24.Fragment.ProfileFragment;
import com.es.netschool24.Fragment.StudentNoticeFragment;


public class ProfileFragmentAdapter extends FragmentPagerAdapter {

    String [] names = {"Profile","Order List","Payment Status"};
    public ProfileFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProfileFragment();
            case 1:
                return new CourseOrderFragment();
            case 2:
                return new StudentNoticeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}

