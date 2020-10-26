package com.example.nutritioncoach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.DayLayoutBinding
import com.example.nutritioncoach.databinding.LayoutMealBinding
import com.example.nutritioncoach.model.Meal

class MealAdapter(var meals :ArrayList<Meal>) :RecyclerView.Adapter<MealAdapter.MealHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val binding= DataBindingUtil.inflate<LayoutMealBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_meal,parent,false)
        return MealHolder(binding)

    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        holder.binding.meal=meals.get(position)
    }

    class  MealHolder(var binding: LayoutMealBinding) : RecyclerView.ViewHolder(binding.root){

    }
}