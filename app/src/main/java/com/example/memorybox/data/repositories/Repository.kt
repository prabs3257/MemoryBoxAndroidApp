package com.example.memorybox.data.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Repository (private val apiService: APIService){


    private lateinit var firebaseAuth: FirebaseAuth


    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var imgLinkLiveData: MutableLiveData<String> = MutableLiveData()
    private var memoriesLiveData:MutableLiveData<List<Memory>> = MutableLiveData()


    suspend fun getMemories(){
        val result = apiService.getMemories()
        if(result?.body()!= null){
            memoriesLiveData.postValue(result.body())
        }

    }




    suspend fun addUser(user: User){
        Log.d("google sign in",user.toString())
        apiService.addUser(user)
    }

    fun getUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun uploadImg(imgUri: Uri){
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imgUri)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    //addMemory(Memory(desc=desc, imgLink = it.toString()))
                    imgLinkLiveData.postValue(it.toString())
                }
            }
    }

    suspend fun addMemory(memory: Memory){

        apiService.addMemory(memory)

    }



    fun googleLogin(account: GoogleSignInAccount?){


        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());

        }else{
            val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("google login", "google login success")
                    Log.d("google login", firebaseAuth.currentUser.toString())


                    userLiveData.postValue(firebaseAuth.currentUser)
                }
        }



    }

    fun logout() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        Log.d("google login", firebaseAuth.currentUser.toString())

    }

    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userLiveData
    }
    fun getImgLinkLiveData() : MutableLiveData<String>{
        return imgLinkLiveData
    }

    fun getMemoriesLiveData() : MutableLiveData<List<Memory>>{
        return memoriesLiveData
    }

}