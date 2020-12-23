package com.ahmdkhled.nutritioncoach.repo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ahmdkhled.nutritioncoach.model.DBResult
import com.ahmdkhled.nutritioncoach.model.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class UserRepo {
    private  val TAG = "UserRepo"
    var db= FirebaseFirestore.getInstance();
    val uid=FirebaseAuth.getInstance().uid
    lateinit var downloadUrl:MutableLiveData<Response<Uri>>

    public suspend fun saveUserData(uid :String,name :String,age:Int ,goal : String,height :Int ,weight :Int):Boolean{
        val user=HashMap<String,Any>()
        user["name"] = name
        user["age"] = age
        user["goal"] = goal
        user["height"] = height
        user["weight"] = weight
        user["level"] = 3

        try {

            db.collection("users")
                .document(uid)
                .set(user)
                .await();
            return true;
        }catch (exception :Exception){
            Log.d(TAG, "saveUserData: "+exception)
            return false;
        }

    }

    public suspend fun updateUserData(field:String,value:Any):Boolean{

        if (uid==null)return false
        try {

            db.collection("users")
                    .document(uid)
                    .update(field,value)
                    .await();
            return true;
        }catch (exception :Exception){
            Log.d(TAG, "updateUserData: "+exception)
            return false;
        }

    }

    suspend fun uploadProfileImage(uri: Uri): MutableLiveData<Response<Uri>> {
        downloadUrl= MutableLiveData()
            val result= FirebaseStorage.getInstance()
                .reference
                .root
                .child("profileImages")
                .child(uid+System.currentTimeMillis())
                .putFile(uri)
                .addOnSuccessListener {taskSnapshot ->
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        Log.d(TAG, "uploadProfileImage: $it")
                        downloadUrl.value=Response(it,false,true,null)

                    }
                }


        return downloadUrl

    }

    suspend fun getUserData(uid:String): DBResult? {
        try {
        val result= db.collection("users")
            .document(uid)
            .get()
            .await()
            return DBResult(result,true,null)

        }catch (e:Exception){
            return DBResult(null,false,e.message)

        }
    }

    fun updateToken(newToken :String){
        if (uid==null)return
        db.collection("users")
            .document(uid)
            .update("token",newToken)
            .addOnCompleteListener { 
                if (it.isSuccessful)
                    Log.d(TAG, "updateToken: ")
                else
                    Log.d(TAG, "updateToken: failed"+it.exception)
            }


    }
}