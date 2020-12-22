package com.ahmdkhled.nutritioncoach.view.dialogs

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.DialogProfileUpdateBinding
import com.ahmdkhled.nutritioncoach.view.ProfileFragment
import com.ahmdkhled.nutritioncoach.viewModel.ProfileFragVM
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileUpdateDialog(val field:String,var onFieldUpdated: OnFieldUpdated) : DialogFragment(){

    lateinit var binding:DialogProfileUpdateBinding
    lateinit var profileFragVM : ProfileFragVM
    private var goal="weight loss"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.dialog_profile_update,container,false)
        profileFragVM=(parentFragment as ProfileFragment).profileFragVM


        handleInput()
        binding.update.setOnClickListener {
            val value=binding.newValue.text.toString()
            if (field!="goal"&&value.isEmpty()){
                Toasty.error(context!!,"field should not be empty").show()
                return@setOnClickListener
            }
            updateUserInfo(value)

        }
        binding.cancel.setOnClickListener { dismiss() }

        return binding.root
    }
    fun updateUserInfo(value: Any) {
        var newValue=value
        if (field!="name"&&field!="goal"){
            newValue=value.toString().toInt()
        }else if (field=="goal"){
            newValue=goal
        }
        binding.update.showProgress{
            buttonTextRes=R.string.loading
        }

        GlobalScope.launch {

            val success=profileFragVM.updateUserData(field,newValue)
            if (success){
                withContext(Dispatchers.Main){
                    onFieldUpdated.onFieldUpdated(field,newValue.toString())
                    dismiss()
                }

            }else{
                Toasty.error(context!!,"error updating field").show()
                binding.update.hideProgress(R.string.try_again)


            }

        }
    }

    fun handleInput(){
        binding.field="Update "+field
        binding.newValue.hint="enter your "+field

        if (field=="goal"){
            binding.newValue.visibility=View.GONE
            binding.goal.visibility=View.VISIBLE
        }

        if (field!="name"&&field!="goal"){
            binding.newValue.inputType=InputType.TYPE_CLASS_NUMBER
        }

        binding.goal.setCheckedTogglePosition(0)
        binding.goal.setOnToggleSwitchChangeListener { position, isChecked ->
            if (position==0&&isChecked){
                binding.goal.setUncheckedTogglePosition(1)
                goal="weight loss"
            }
            else if (position==1&&isChecked){
                binding.goal.setUncheckedTogglePosition(0)
                goal="gain weight"
            }

        }
    }

    interface OnFieldUpdated{
        fun onFieldUpdated(field:String,newValue :String)
    }

    override fun onResume() {
        super.onResume()
        if (dialog != null && dialog!!.window != null)
            dialog!!.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}