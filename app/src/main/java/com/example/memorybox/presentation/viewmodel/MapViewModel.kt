package com.example.memorybox.presentation.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.models.User
import com.example.memorybox.data.repositories.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.MapView
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val repository: Repository) : ViewModel()  {

    private var memoryLiveData:MutableLiveData<List<Memory>> = repository.getMemoriesLiveData()

    fun getMemories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMemories()
        }
    }

    fun getMemoryLiveData() : MutableLiveData<List<Memory>>{
        return memoryLiveData
    }


}