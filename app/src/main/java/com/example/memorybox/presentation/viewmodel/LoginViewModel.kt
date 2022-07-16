package com.example.memorybox.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private var userLiveData:MutableLiveData<FirebaseUser> = repository.getUserLiveData()

    fun googleLogin(account: GoogleSignInAccount?){
        repository.googleLogin(account)
    }
    fun logout(){
        repository.logout()
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun getUserLiveData() : MutableLiveData<FirebaseUser>{
        return userLiveData
    }
}