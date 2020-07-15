package com.example.nutritioncoach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nutritioncoach.databinding.ActivityMainBinding
import com.example.nutritioncoach.view.MainFrag
import com.example.nutritioncoach.view.RegisterFrag
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val registerFrag=RegisterFrag();

        val currentUser= Firebase.auth.currentUser
        if (currentUser==null)
            goTo(registerFrag)
        else
            goTo(MainFrag())


    }


    public fun goTo(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment,"")
            .commit()
    }
}