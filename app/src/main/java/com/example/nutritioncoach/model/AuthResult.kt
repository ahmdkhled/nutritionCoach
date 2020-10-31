package com.example.nutritioncoach.model

import com.google.firebase.auth.AuthResult

data class AuthResult(var authResult: AuthResult?,var isSuccessfull :Boolean,var errorMessage :String?) {

}