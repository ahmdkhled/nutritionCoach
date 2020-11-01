package com.example.nutritioncoach.view

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.FragmentLoginBinding
import com.example.nutritioncoach.viewModel.LoginFragVM
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFrag :Fragment() {

    lateinit var loginFragVM:LoginFragVM
    lateinit var  binding:FragmentLoginBinding
    private  val TAG = "LoginFrag"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginFragVM=ViewModelProvider(this).get(LoginFragVM::class.java)
        binding=DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login,container,false)

        binding.login.setOnClickListener {
            login(binding.email.text.toString(),binding.password.text.toString())

        }

        binding.register.setOnClickListener {
            (activity as MainActivity).loadFragment(RegisterFragment())
        }
        return binding.root
    }

    fun login(email:String,password:String){
        if (TextUtils.isEmpty(email)){
            context?.let{Toasty.error(it,"Email is empty",Toasty.LENGTH_LONG).show()}
            return
        }
        if (TextUtils.isEmpty(password)){
            context?.let{Toasty.error(it,"Password is empty",Toasty.LENGTH_LONG).show()}
            return
        }
        binding.login.showProgress{
            buttonTextRes = R.string.loading
            progressColor= Color.WHITE
        }

        GlobalScope.launch (Dispatchers.IO){
            val result=loginFragVM.login(email,password)
            Log.d(TAG, "login: "+result.isSuccessfull)
            withContext(Dispatchers.Main){
                binding.login.hideProgress(R.string.login)
                if (result.isSuccessfull){
                    Log.d(TAG, "sucess in: ")
                    (activity as MainActivity).loadFragment(DashboardFragment())
                }else
                    context?.let { Toasty.error(it,"login failed "+result.errorMessage).show() }
            }



        }
    }





}