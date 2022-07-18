package com.exnin.onlinelearning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.exnin.onlinelearning.AppConstants.AppConstants;
import com.exnin.onlinelearning.Models.Banners;
import com.exnin.onlinelearning.R;
import com.exnin.onlinelearning.ViewHolders.SliderViewHolder;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    private Context context;
    private List<Banners> bannersList;


    public SliderAdapter(Context context, List<Banners> bannersList) {
        this.context = context;
        this.bannersList = bannersList;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Banners banners = bannersList.get(position);

        /*Glide.with(viewHolder.itemView)
                .load(AppConstants.banner_image_path+banners.getBannerImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);*/

        Picasso.get().load(AppConstants.banner_image_path+banners.getBannerImage()).into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return bannersList.size();
    }
}
