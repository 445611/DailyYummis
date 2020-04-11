package com.example.dailyyummies.meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MealViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply{
        value = "Imaginee there are your favorite meals"
    }
    val text: LiveData<String> = _text

    fun setName(text: String){
        _text.postValue(text)
    }
}