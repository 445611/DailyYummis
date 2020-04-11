package com.example.dailyyummies.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply{
        value = "What would you like to eat today? "
    }
    val text: LiveData<String> = _text

    fun setName(text: String){
        _text.postValue(text)
    }
}