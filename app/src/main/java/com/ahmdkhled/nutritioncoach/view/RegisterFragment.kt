package com.ahmdkhled.nutritioncoach.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ahmdkhled.nutritioncoach.R
import com.ahmdkhled.nutritioncoach.databinding.FragmentRegisterBinding
import com.ahmdkhled.nutritioncoach.repo.AuthRepo
import com.ahmdkhled.nutritioncoach.viewModel.RegisterVM
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.coroutines.*

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var registerVM: RegisterVM;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,R.layout.fragment_register,container,false)
        registerVM=ViewModelProvider(this).get(RegisterVM::class.java)


        binding.register.setOnClickListener { view ->
            if (!validateInput(binding.email,
                    binding.password)){
                return@setOnClickListener;

            }
            binding.register.showProgress{
                buttonTextRes = R.string.loading
                progressColor=Color.WHITE
            }

            GlobalScope.launch(Dispatchers.IO) {


                Log.d("TAG", "loading: ")

                val result = AuthRepo().register(
                    binding.email.text.toString(),
                    binding.password.password.text.toString()
                )
                if (result.isSuccessfull!!) {
                    withContext(Dispatchers.Main){
                        binding.register.hideProgress(R.string.done)
                        clearFields(binding.email, binding.password)
                        Log.d("TAG", " success " + result.authResult?.user?.uid)
                        (activity as MainActivity).loadFragment(AddInfoFragment());
                    }


                } else{
                    Log.d("TAG", "error: ")
                    withContext(Dispatchers.Main) {
                        binding.register.hideProgress(R.string.try_again)

                        context?.let { Toasty.error(it,
                        result.errorMessage.toString(), Toast.LENGTH_SHORT, true).show() }

                }
                }


            }
        }

        binding.login.setOnClickListener {
            (activity as MainActivity).loadFragment(LoginFrag())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=Firebase.auth
    }

    private fun validateInput(email: EditText,password: EditText):Boolean{
        if(email.text.isEmpty()){
            context?.let { Toasty.error(it,"email is required",Toasty.LENGTH_LONG).show() }
            return false
        }
        if(password.text.isEmpty()){
            context?.let { Toasty.error(it,"password is required",Toasty.LENGTH_LONG).show() }
            return false
        }

        return true
    }

    private fun clearFields(email :EditText,password :EditText){
        email.setText("")
        password.setText("")
    }


}