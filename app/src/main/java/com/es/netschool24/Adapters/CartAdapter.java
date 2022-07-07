package com.es.netschool24.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.es.netschool24.AppConstants.AppConstants;
import com.es.netschool24.Models.Cart;
import com.es.netschool24.Models.CartCourse;
import com.es.netschool24.Models.CartCourseMain;
import com.es.netschool24.MyApi;
import com.es.netschool24.MyRetrofit;
import com.es.netschool24.R;
import com.es.netschool24.ViewHolders.CartViewHolder;
import com.es.netschool24.interfaces.CartDeleteListener;
import com.es.netschool24.storage.SharedPrefManager;

import org.jsoup.Jsoup;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    Context context;
    //List<CartCourse> cartCourseList;
    List<Cart> cartList;

    CartDeleteListener cartDeleteListener;

   /* public CartAdapter(Context context, List<CartCourse> cartCourseList) {
        this.context = context;
        this.cartCourseList = cartCourseList;
    }*/

    public CartAdapter(Context context, List<Cart> cartList, CartDeleteListener cartDeleteListener) {
        this.context = context;
        this.cartList = cartList;
        this.cartDeleteListener = cartDeleteListener;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //CartCourse cartCourse = cartCourseList.get(position);
        Cart cart = cartList.get(position);


        //cartList.remove(position);
        //notifyDataSetChanged();


        String title = Jsoup.parse(cart.getCourse().getName()).text();

        Glide.with(context).load(AppConstants.course_image_path + cart.getCourse().getBannerImage()).into(holder.cart_course_img);
        holder.cart_course_title.setText(title);
        holder.cart_price_txt.setText(cart.getCourse().getCourseFee().toString());
        holder.cart_pay_txt.setText(cart.getCourse().getInstallments().get(0).getBdt());

        if (cart.getCourse().getInstallments().size() > 0) {
            switch (cart.getCourse().getInstallments().size()) {

                case 1:
                    holder.cart_installment_txt1.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt1.setText("1st inst." + cart.getCourse().getInstallments().get(0).getBdt());

                    break;
                case 2:
                    holder.cart_installment_txt1.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt2.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt1.setText("1st inst." + cart.getCourse().getInstallments().get(0).getBdt());
                    holder.cart_installment_txt2.setText("2nd inst." + cart.getCourse().getInstallments().get(1).getBdt());
                    break;
                case 3:
                    holder.cart_installment_txt1.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt2.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt3.setVisibility(View.VISIBLE);
                    holder.cart_installment_txt1.setText("1st inst." + cart.getCourse().getInstallments().get(0).getBdt());
                    holder.cart_installment_txt2.setText("2nd inst." + cart.getCourse().getInstallments().get(1).getBdt());
                    holder.cart_installment_txt3.setText("3rd inst." + cart.getCourse().getInstallments().get(2).getBdt());
                    break;

            }
        } else {
            holder.cart_pay_txt.setText("");
            holder.cart_installment_txt1.setText("");
            holder.cart_installment_txt2.setText("");
            holder.cart_installment_txt3.setText("");
        }



        holder.cart_course_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Item").setMessage("Are you sure to delete it?").setIcon(R.drawable.ic_baseline_delete_24);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartDeleteListener.deleteCart(cart);



                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();




            }
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
