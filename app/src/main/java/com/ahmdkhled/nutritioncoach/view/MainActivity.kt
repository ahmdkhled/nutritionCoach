package com.ahmdkhled.nutritioncoach.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    lateinit var onProfileImageLoaded: OnProfileImageLoaded

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

         binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
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

        FirebaseMessaging.getInstance()
            .token.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("TOKENN", task.result)
                }
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode $data")
        if (requestCode==ProfileFragment.PICK_IMAGE_REQUEST_CODE&&resultCode==RESULT_OK){
            if (data!=null){
                val uri=data.data
                if (this::onProfileImageLoaded.isInitialized){
                    onProfileImageLoaded.onProfileImageLoaded(uri)
                }

            }
        }
    }

     fun loadFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
    fun addFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer,fragment)
            .addToBackStack(null)
            .commit()
    }


    fun setBottomNavigationVisibility(visibility: Int) {
        binding.bottomNavigationView.visibility=visibility
    }

    interface OnProfileImageLoaded{
        fun onProfileImageLoaded(uri: Uri?)
    }
}