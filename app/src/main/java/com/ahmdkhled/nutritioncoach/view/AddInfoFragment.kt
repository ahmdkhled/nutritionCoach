package com.ahmdkhled.nutritioncoach.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.FragmentAddInfoBinding
import com.ahmdkhled.nutritioncoach.viewModel.AddInfoFragVM
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddInfoFragment :Fragment() {

    lateinit var addInfoFragVM:AddInfoFragVM;
    var goal:String="";
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=DataBindingUtil.inflate<FragmentAddInfoBinding>(inflater, R.layout.fragment_add_info,container,false)

        addInfoFragVM=ViewModelProvider(this).get(AddInfoFragVM::class.java)

        binding.save.setOnClickListener {
            val uid= Firebase.auth.currentUser?.uid
            if (uid==null)return@setOnClickListener
            binding.save.showProgress{
                buttonTextRes=R.string.loading
            }
            viewLifecycleOwner.lifecycleScope.launch {
                val success=addInfoFragVM.saveUserData(
                    uid, binding.nameIL.editText?.text.toString(),
                    binding.ageIL.editText?.text.toString().toInt(),
                    goal,
                    binding.heightIL.editText?.text.toString().toInt(),
                    binding.weightIL.editText?.text.toString().toInt()
                )
                if (success){
                    withContext(Dispatchers.Main){
                        (activity as MainActivity).loadFragment(DashboardFragment())
                        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
                        binding.save.hideProgress(R.string.done)
                    }


                }else{
                    withContext(Dispatchers.Main){
                        binding.save.hideProgress(R.string.try_again)
                        context?.let { it1 -> Toasty.error(it1,"Error saving data , try again",Toasty.LENGTH_LONG).show() }
                    }
                }
            }
        }

        binding.goal.setOnToggleSwitchChangeListener { position, isChecked ->
            if (position==0&&isChecked){
                binding.goal.setUncheckedTogglePosition(1)
                goal="weight loss"
            }
            else if (position==1&&isChecked)
                binding.goal.setUncheckedTogglePosition(0)
                goal="gain weight"

        }


        return binding.root
    }
}