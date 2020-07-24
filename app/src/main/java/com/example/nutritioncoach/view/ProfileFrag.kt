package com.example.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.FragmentProfileBinding
import com.example.nutritioncoach.viewModel.ProfileFragVM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class ProfileFrag :Fragment() {

    private  val TAG = "ProfileFragg"
    lateinit var profileFragVM :ProfileFragVM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile,container,false)
        profileFragVM=ViewModelProvider(this).get(ProfileFragVM::class.java)
        val uid=Firebase.auth.uid;

         GlobalScope.launch {
             if (uid==null)return@launch
             val info=profileFragVM.getUserInfo(uid)
             Log.d(TAG, "onCreateView: "+info?.documentSnapshot?.data)
             withContext(Dispatchers.Main){
                 populateInfo(binding,info?.documentSnapshot?.data)
             }

         }


        return binding.root
    }

    fun populateInfo(
        binding:FragmentProfileBinding,
        info: MutableMap<String, Any>?
    ){
        if (info==null)return
        binding.setVariable(R.id.info,info.toMap())
        binding.name.text = info["name"].toString()
        binding.goal.text=info["goal"].toString()
        binding.height.text=info["height"].toString()
        binding.weightGoal.text=info["weightGoal"].toString()
        val weight =info["weight"].toString().trim()
        val height =info["height"].toString().trim()
        binding.weightGauge.moveToValue(weight.toFloat() )
        binding.weight.text=weight+" KG"
        val bmi=(weight.toFloat() /(height.toFloat()/100).pow(2))



        binding.bmiGauge.moveToValue(bmi)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        binding.bmi.text=df.format(bmi)
        context?.let {
            Glide
                .with(it)
                .load(info["image"])
                .into(binding.image)
        }
    }
}