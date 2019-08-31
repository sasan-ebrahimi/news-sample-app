package com.s_ebrahimi.newssample.util

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.s_ebrahimi.newssample.R

/**
 * A helper class to load image to ImageViw
 */
class ImageLoaderHelper{

    companion object{

        /**
         * Loads a url to an ImageView
         * If the url is not valid, loads an static resource on ImageView
         * @param url : image url
         * @param imageView : ImageView
         */
        fun loadUrlToImageView(url:String? , imageView : ImageView){
            if(url != null && url.startsWith("http") && imageView != null){
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView)
            }else{
                Picasso.get()
                    .load(R.drawable.placeholder_no_image)
                    .placeholder(R.drawable.placeholder_no_image)
                    .into(imageView)
            }
        }
    }

}