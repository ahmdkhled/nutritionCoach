package com.ahmdkhled.nutritioncoach.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.FragmentProfileBinding
import com.ahmdkhled.nutritioncoach.model.UserInfo
import com.ahmdkhled.nutritioncoach.view.dialogs.ProfileUpdateDialog
import com.ahmdkhled.nutritioncoach.viewModel.ProfileFragVM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class ProfileFragment :Fragment(),ProfileUpdateDialog.OnFieldUpdated,
    MainActivity.OnProfileImageLoaded {

    private  val TAG = "ProfileFragg"
    lateinit var profileFragVM :ProfileFragVM
    lateinit var binding:FragmentProfileBinding
    val uid=Firebase.auth.uid

    companion object{
        val PICK_IMAGE_REQUEST_CODE=156

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        profileFragVM=ViewModelProvider(this).get(ProfileFragVM::class.java)
        binding.edit=View.GONE

         GlobalScope.launch {
             if (uid==null)return@launch
             val info=profileFragVM.getUserInfo(uid)
             Log.d(TAG, "onCreateView: "+info?.documentSnapshot?.data)
             val userInfo=info?.documentSnapshot?.toObject(UserInfo::class.java)
             withContext(Dispatchers.Main){
                 populateInfo(binding,userInfo)
             }

         }

        binding.update.setOnClickListener {
            binding.edit=View.VISIBLE
        }

        handleUpdate()
        binding.updatePhoto.setOnClickListener {
            val intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            activity?.startActivityForResult(intent,PICK_IMAGE_REQUEST_CODE)
        }

        return binding.root
    }

    fun populateInfo(
        binding:FragmentProfileBinding,
        userInfo: UserInfo?
    ){
        if (userInfo==null ||uid==null)return

        Log.d(TAG, "populateInfo: "+userInfo.toString())
        val weight= userInfo.weight
        val height= userInfo.height
        if (weight!=null&&height!=null){
            binding.weightGauge.moveToValue(weight.toFloat())
            val bmi=(weight.toFloat() /(height.toFloat()/100).pow(2))
            binding.bmiGauge.moveToValue(bmi)
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            binding.bmi.text=df.format(bmi)
        }
        binding.info=userInfo
        binding.weight.startAnimation(AnimationUtils.loadAnimation(context,R.anim.tv_animation))
        binding.bmi.startAnimation(AnimationUtils.loadAnimation(context,R.anim.tv_animation))

        context?.let {
            Glide
                .with(it)
                .load(userInfo.image)
                .placeholder(R.drawable.profile)
                .into(binding.image)

        }
    }


    fun handleUpdate(){
        binding.updateName.setOnClickListener {
            val profileUpdateDialog=ProfileUpdateDialog("name",this)
            profileUpdateDialog.show(childFragmentManager,"")
        }
        binding.updateHeight.setOnClickListener {
            val profileUpdateDialog=ProfileUpdateDialog("height",this)
            profileUpdateDialog.show(childFragmentManager,"")
        }
        binding.updateWeight.setOnClickListener {
            val profileUpdateDialog=ProfileUpdateDialog("weight",this)
            profileUpdateDialog.show(childFragmentManager,"")
        }
        binding.updateWeightGoal.setOnClickListener {
            val profileUpdateDialog=ProfileUpdateDialog("weightGoal",this)
            profileUpdateDialog.show(childFragmentManager,"")
        }
        binding.updateGoal.setOnClickListener {
            val profileUpdateDialog=ProfileUpdateDialog("goal",this)
            profileUpdateDialog.show(childFragmentManager,"")
        }

        (activity as MainActivity).onProfileImageLoaded=this
    }

    override fun onFieldUpdated(field: String, newValue: String) {

        if (field == "name"){ binding.name.text=newValue }
        if (field == "weight"){ binding.weight.text=newValue }
        if (field == "height"){ binding.height.text=newValue }
        if (field == "weightGoal"){ binding.weightGoal.text=newValue }
        if (field == "goal"){ binding.goal.text=newValue }
    }

    override fun onProfileImageLoaded(uri: Uri?) {
        if (uri==null)return
        viewLifecycleOwner.lifecycleScope.launch {
            val response=profileFragVM.uploadProfileImage(uri)
                .observe(viewLifecycleOwner, Observer{response->
                    if (response.success){
                        val downloadUri=response.model
                        viewLifecycleOwner.lifecycleScope.launch {
                            val success=profileFragVM.updateUserData("image",downloadUri.toString().trim())
                            if (success){
                                binding.image.setImageURI(uri)

                            }
                        }
                    }
                })


        }
    }


}