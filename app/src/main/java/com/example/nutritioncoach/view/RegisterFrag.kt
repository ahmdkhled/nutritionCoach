package com.example.nutritioncoach.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutritioncoach.R
import com.example.nutritioncoach.databinding.FragmentRegisterBinding
import com.example.nutritioncoach.repo.AuthRepo
import com.example.nutritioncoach.viewModel.RegisterVM
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RegisterFrag : Fragment() {

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
            GlobalScope.launch(Dispatchers.IO) {
                Log.d("TAG", "loading: ")
                val result=AuthRepo().register(binding.email.text.toString(),binding.password.password.text.toString())
                if (result.isSuccessfull!!){
                    clearFields(binding.email,binding.password)
                    Log.d("TAG", " success "+ result.authResult?.user?.uid )
                    goTo()


                }else
                    Toast.makeText(context,result.errorMessage,Toast.LENGTH_LONG).show()

            }


        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=Firebase.auth
    }

    private fun clearFields(email :EditText,password :EditText){
        email.setText("")
        password.setText("")
    }
    private fun goTo(fragment :Fragment){
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }

    private fun register(email :String ,password :String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                task ->
                if(task.isSuccessful){
                    Toast.makeText(context,"registered successfully",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(context,"registeration error",Toast.LENGTH_LONG).show();

            }
    }
}