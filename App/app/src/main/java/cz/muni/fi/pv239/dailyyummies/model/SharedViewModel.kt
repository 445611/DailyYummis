package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import cz.muni.fi.pv239.dailyyummies.service.ZomatoService
import cz.muni.fi.pv239.dailyyummies.service.networking.data.SearchResult

class SharedViewModel(var mapCoordinates: LatLng? = null, var radius: Int? = null): ViewModel() {

    private lateinit var zomatoService: ZomatoService

    lateinit var sharedPreferences: SharedPreferences

    private var _searchResult = MutableLiveData<SearchResult>(SearchResult())

    var searchResult: LiveData<SearchResult> = _searchResult

    fun initSharedPreferences(context: Context) {
        sharedPreferences =
            SharedPreferences(context)
        zomatoService = ZomatoService(context, _searchResult)
        //fetchApiData(context)
    }

    fun fetchApiData(context: Context) {
        if (mapCoordinates != null) {
            zomatoService.fetchRestaurantsData(
                mapCoordinates!!,
                radius ?: sharedPreferences.getDefaultRadius()
            )
        }
    }
}