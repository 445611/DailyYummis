package com.example.dailyyummies.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply{
        value = "Imaginee there is a map"
    }
    val text: LiveData<String> = _text

    fun setName(text: String){
        _text.postValue(text)
    }
}