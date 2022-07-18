package com.exnin.onlinelearning.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.exnin.onlinelearning.Models.CourseOrder;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.ViewHolders.OrderViewHolder;

import org.jsoup.Jsoup;

import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    Context context;
    List<CourseOrder> courseOrderList;

    Date date;

    public OrderAdapter(Context context, List<CourseOrder> courseOrderList) {
        this.context = context;
        this.courseOrderList = courseOrderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        CourseOrder courseOrder = courseOrderList.get(position);
        String name = Jsoup.parse(courseOrder.getCourseOrderInfo().getName()).text();
        holder.course_name.setText(name);
        holder.course_fee.setText("Fee: "+courseOrder.getPrice());
        holder.course_day.setText("Day: "+courseOrder.getSelectedDay());
        holder.course_time.setText("Time: "+ courseOrder.getSelectedTime().substring(11,16));
        holder.joining_date.setText("Join Date: "+courseOrder.getCreatedAt().substring(0,10));

        if (courseOrder.getStatus().equals("1")){
            holder.course_status.setText("Running");
        }else if (courseOrder.getStatus().equals("2")){
            holder.course_status.setText("Complete");
        }else {
            holder.course_status.setText("Dropped");
        }

        for (int i=0; i<courseOrder.getOrderInstallments().size(); ++i){
            switch (i){
                case 0:
                    holder.inst_1.setText(courseOrder.getOrderInstallments().get(0).getBdt());
                    holder.pay_date_1.setText(courseOrder.getOrderInstallments().get(0).getPaydate().substring(0,10));
                    if (courseOrder.getOrderInstallments().get(0).getStatus().equals("1")){
                        holder.status_1.setText("Unpaid");
                        holder.action_1.setVisibility(View.VISIBLE);
                    }else {
                        holder.status_1.setText("Paid");
                    }
                    break;

                case 1:
                    holder.inst_2.setText(courseOrder.getOrderInstallments().get(1).getBdt());
                    holder.pay_date_2.setText(courseOrder.getOrderInstallments().get(1).getPaydate().substring(0,10));
                    if (courseOrder.getOrderInstallments().get(1).getStatus().equals("1")){
                        holder.status_2.setText("Unpaid");
                        holder.action_2.setVisibility(View.VISIBLE);
                    }else {
                        holder.status_2.setText("Paid");
                    }
                    break;


                case 2:
                    holder.inst_3.setVisibility(View.VISIBLE);
                    holder.inst_3.setText(courseOrder.getOrderInstallments().get(2).getBdt());
                    holder.pay_date_3.setVisibility(View.VISIBLE);
                    holder.pay_date_3.setText(courseOrder.getOrderInstallments().get(2).getPaydate().substring(0,10));
                    if (courseOrder.getOrderInstallments().get(1).getStatus().equals("1")){
                        holder.status_3.setVisibility(View.VISIBLE);
                        holder.status_3.setText("Unpaid");
                        holder.action_3.setVisibility(View.VISIBLE);
                    }else {
                        holder.status_3.setVisibility(View.VISIBLE);
                        holder.status_3.setText("Paid");
                    }
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return courseOrderList.size();
    }
}
