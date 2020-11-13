package com.example.nutritioncoach.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Binder {

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun load(imageview:ImageView, url:String?){
            //todo implement binding adapter
            Log.d("Binderr", "load: "+url)
            if (url==null) return
            Glide
                .with(imageview.context)
                .load(url)
                .into(imageview)
        }
    }
}