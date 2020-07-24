package com.example.nutritioncoach.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Binder {
    @BindingAdapter("android:src")
    public fun load(imageview:ImageView,url:String){
        Log.d("Binderr", "load: "+url)
        Glide
            .with(imageview.context)
            .load(url)
            .into(imageview)
    }
}