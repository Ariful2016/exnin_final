package com.exnin.onlinelearning.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exnin.onlinelearning.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseViewHolder extends RecyclerView.ViewHolder {
    public TextView courseName;
    public CircleImageView course_img;

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);

        courseName = itemView.findViewById(R.id.course_name);
        course_img = itemView.findViewById(R.id.course_img);


    }
}
