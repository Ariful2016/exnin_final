package com.exnin.onlinelearning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exnin.onlinelearning.Models.Cart;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.ViewHolders.CartCheckOutViewHolder;

import org.jsoup.Jsoup;

import java.util.List;

public class CartCheckoutAdapter extends RecyclerView.Adapter<CartCheckOutViewHolder> {

    Context context;
    List<Cart> cartList;

    public CartCheckoutAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartCheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_checkout, parent, false);
        return new CartCheckOutViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartCheckOutViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        String title = Jsoup.parse(cart.getCourse().getName()).text();
        holder.courseName_txt.setText(title);
        holder.first_installment_bdt.setText(cart.getCourse().getInstallments().get(0).getBdt()+" TK.");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
