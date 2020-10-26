package com.example.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritioncoach.R
import com.example.nutritioncoach.adapters.PlanDaysAdapter
import com.example.nutritioncoach.databinding.FragmentDashboardBinding
import com.example.nutritioncoach.model.Day
import com.example.nutritioncoach.model.Meal
import com.example.nutritioncoach.viewModel.DashboardFragVM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    lateinit var  dashboardFragVM: DashboardFragVM
    lateinit var  binding:FragmentDashboardBinding
    private  val TAG = "DashboardFragmentt"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dashboardFragVM=ViewModelProvider(this).get(DashboardFragVM::class.java)
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false)



        GlobalScope.launch {
            val dietResult=dashboardFragVM.getDietPlan(Firebase.auth.uid)
            Log.d(TAG, "uid : "+Firebase.auth.uid)
            if (dietResult.isSuccessfull){
                Log.d(TAG, "onCreateView: "+ dietResult.plan!!.days)
                val adapter=PlanDaysAdapter(dietResult.plan!!.days,context)

                withContext(Dispatchers.Main){
                    binding.planRecycler.adapter=adapter
                    binding.planRecycler.layoutManager= LinearLayoutManager(context)
                    val todayIndex= Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                    binding.planRecycler.scrollToPosition(todayIndex)


                }


                //binding.planRecycler.layoutManager= StackCardLayoutManager(1)

            }else
                Log.d(TAG, "error: "+dietResult.errorMessage)

        }
        return binding.root
    }

    fun getFakeDays(): ArrayList<Day> {
        val days=ArrayList<Day>();
        val breakfast=ArrayList<Meal>();
        val launch=ArrayList<Meal>();
        val dinner=ArrayList<Meal>();

        breakfast.add(Meal(100.0,"gm","cheses"))
        breakfast.add(Meal(100.0,"gm","cheses"))
        breakfast.add(Meal(100.0,"gm","cheses"))
        launch.add(Meal(100.0,"gm","meat"))
        dinner.add(Meal(1.0,"plate","salad"))

        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        days.add(Day(breakfast,launch,dinner))
        return days
    }
    
}