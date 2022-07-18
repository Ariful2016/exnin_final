package com.exnin.onlinelearning.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.exnin.onlinelearning.Fragment.StudentNoticeFragment;
import com.exnin.onlinelearning.Fragment.TeacherProfileFragment;
import com.exnin.onlinelearning.Fragment.TeacherStatusFragment;

public class TeacherProfileFragmentAdapter extends FragmentPagerAdapter {
    String [] names = {"Profile", "Status", "Notice"};

    public TeacherProfileFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TeacherProfileFragment();
            case 1:
                return new TeacherStatusFragment();
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
