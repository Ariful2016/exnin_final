package com.exnin.onlinelearning.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.exnin.onlinelearning.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView course_name,course_fee,course_day,
            course_time,joining_date,inst_1,
            pay_date_1,inst_2,pay_date_2,inst_3,pay_date_3;

    public AppCompatButton course_status,status_1,action_1,status_2,action_2,status_3,action_3;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        course_name  = itemView.findViewById(R.id.course_name);
        course_fee  = itemView.findViewById(R.id.course_fee);
        course_day  = itemView.findViewById(R.id.course_day);
        course_status  = itemView.findViewById(R.id.course_status);
        course_time  = itemView.findViewById(R.id.course_time);
        joining_date  = itemView.findViewById(R.id.joining_date);
        inst_1  = itemView.findViewById(R.id.inst_1);
        pay_date_1  = itemView.findViewById(R.id.pay_date_1);
        inst_2  = itemView.findViewById(R.id.inst_2);
        pay_date_2  = itemView.findViewById(R.id.pay_date_2);
        inst_3  = itemView.findViewById(R.id.inst_3);
        pay_date_3  = itemView.findViewById(R.id.pay_date_3);
        status_1  = itemView.findViewById(R.id.status_1);
        action_1  = itemView.findViewById(R.id.action_1);
        status_2  = itemView.findViewById(R.id.status_2);
        action_2  = itemView.findViewById(R.id.action_2);
        status_3  = itemView.findViewById(R.id.status_3);
        action_3  = itemView.findViewById(R.id.action_3);

    }
}
