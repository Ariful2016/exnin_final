package com.exnin.onlinelearning.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import com.exnin.onlinelearning.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderViewHolder extends SliderViewAdapter.ViewHolder {


    public View itemView;
    public ImageView imageViewBackground;
    //public ImageView imageGifContainer;
    //public TextView textViewDescription;

    public SliderViewHolder(View itemView) {
        super(itemView);

        imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
       // imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
        //textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
        this.itemView = itemView;
    }
}
