package com.example.nutritioncoach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nutritioncoach.databinding.ActivityMainBinding
import com.example.nutritioncoach.view.ChatFragment
import com.example.nutritioncoach.view.DashboardFragment
import com.example.nutritioncoach.view.ProfileFragment
import com.example.nutritioncoach.view.RegisterFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when{
                menuItem.itemId == R.id.navigationDashboard -> {
                    loadFragment(DashboardFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.navigationProfile -> {
                    loadFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.navigationChat -> {
                    loadFragment(ChatFragment())
                    return@setOnNavigationItemSelectedListener true

                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        val registerFrag=RegisterFragment();
        val currentUser= Firebase.auth.currentUser
        if (currentUser==null)
            loadFragment(registerFrag)
        else
            loadFragment(DashboardFragment())
    }


     fun loadFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment,"")
            .commit()
    }
}