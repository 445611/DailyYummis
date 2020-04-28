package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.muni.fi.pv239.dailyyummies.model.SharedPreferences

class SharedViewModel: ViewModel() {

    lateinit var sharedPreferences: SharedPreferences

    private var _sharedText = MutableLiveData<String>()

    var sharedText: LiveData<String> = _sharedText

    fun setSharedText(liveData: String) {
        _sharedText.postValue(liveData)
    }

    fun initSharedPreferences(context: Context) {
        sharedPreferences =
            SharedPreferences(context)
    }
}