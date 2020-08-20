package com.example.nutritioncoach.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritioncoach.R
import com.example.nutritioncoach.repo.DietRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DashboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        GlobalScope.launch {
            DietRepo().getCurrentPlan(Firebase.auth.uid)

        }
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
    
}