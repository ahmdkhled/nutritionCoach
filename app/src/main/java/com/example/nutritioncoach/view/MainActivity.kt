package com.example.nutritioncoach.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
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
                    loadFragment(ConversationsFrag())
                    return@setOnNavigationItemSelectedListener true

                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        val loginFrag=LoginFrag();
        val currentUser= Firebase.auth.currentUser
        if (currentUser==null){
            binding.bottomNavigationView.visibility= View.GONE
            loadFragment(loginFrag)
        }
        else{
            binding.bottomNavigationView.selectedItemId=
                R.id.navigationDashboard
            loadFragment(DashboardFragment())
        }
    }


     fun loadFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}