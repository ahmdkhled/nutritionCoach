package com.example.nutritioncoach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.nutritioncoach.databinding.ActivityMainBinding
import com.example.nutritioncoach.view.RegisterFrag
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val registerFrag=RegisterFrag();

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container,registerFrag,"")
            .commit()
    }
}