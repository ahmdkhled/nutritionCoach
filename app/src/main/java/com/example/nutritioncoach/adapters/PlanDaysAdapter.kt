package com.example.nutritioncoach.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.DayLayoutBinding
import com.example.nutritioncoach.model.Day
import com.example.nutritioncoach.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class PlanDaysAdapter(var daysPlan: ArrayList<Day>, val context: Context?) : RecyclerView.Adapter<PlanDaysAdapter.PlanDaysViewHolder>() {

    private  val TAG = "PlanDaysAdapter"
    init {
        Log.d("DashboardFragmentt", "constructor "+daysPlan.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDaysViewHolder {
        Log.d("DashboardFragmentt", "onCreateViewHolder: ")
        val binding=DataBindingUtil.inflate<DayLayoutBinding>(LayoutInflater.from(parent.context),R.layout.day_layout,parent,false)
        return PlanDaysViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("DashboardFragmentt", "getItemCount: "+daysPlan.size)
        return daysPlan.size
    }

    override fun onBindViewHolder(holder: PlanDaysViewHolder, position: Int) {
        Log.d("DashboardFragmentt", "onBindViewHolder: ")
        val breakfastAdapter=MealAdapter(daysPlan.get(position).breakfast)
        val launchAdapter=MealAdapter(daysPlan.get(position).launch)
        val dinnerAdapter=MealAdapter(daysPlan.get(position).dinner)
        holder.binding.breakfast.adapter=breakfastAdapter
        holder.binding.breakfast.layoutManager=LinearLayoutManager(context)

        holder.binding.launch.adapter=launchAdapter
        holder.binding.launch.layoutManager=LinearLayoutManager(context)

        holder.binding.dinner.adapter=dinnerAdapter
        holder.binding.dinner.layoutManager=LinearLayoutManager(context)

        val todayIndex=Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        holder.binding.day.text= context?.let { Constants.getWeekDays(it).get(position) }

        Log.d(TAG, "onBindViewHolder: "+todayIndex)
        if(todayIndex==position){
            holder.binding.day.setTextColor(Color.parseColor("#3ECC4B"))
            holder.binding.container.setBackgroundResource(R.drawable.selected_card_bg)

        }

    }

    class PlanDaysViewHolder(val binding: DayLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        init {
            Log.d("DashboardFragmentt", "PlanDaysViewHolder constructor")
        }
    }
}