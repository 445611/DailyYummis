package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import cz.muni.fi.pv239.dailyyummies.service.ZomatoService
import cz.muni.fi.pv239.dailyyummies.service.networking.data.CuisineResult
import cz.muni.fi.pv239.dailyyummies.service.networking.data.RestaurantSearchResult

class SharedViewModel(var mapCoordinates: LatLng? = null, var radius: Int? = null): ViewModel() {

    private lateinit var zomatoService: ZomatoService

    lateinit var sharedPreferences: SharedPreferences

    private var _restaurantsSearchResult = MutableLiveData<RestaurantSearchResult>(RestaurantSearchResult())
    private var _cuisinesSearchResult = MutableLiveData<CuisineResult>(CuisineResult())

    var restaurantsSearchResult: LiveData<RestaurantSearchResult> = _restaurantsSearchResult
    var cuisinesSearchResult: LiveData<CuisineResult> = _cuisinesSearchResult

    fun initSharedPreferences(context: Context) {
        sharedPreferences =
            SharedPreferences(context)
        zomatoService = ZomatoService(context, _restaurantsSearchResult, _cuisinesSearchResult)
    }

    fun fetchApiCuisinesData() {
        _cuisinesSearchResult.postValue(CuisineResult())
        if (!sharedPreferences.getDefaultHome().isNullOrEmpty()) {
            zomatoService.fetchCuisinesForCityData(sharedPreferences.getDefaultHome())
        }
    }

    fun fetchApiRestaurantsData() {
        if (mapCoordinates != null) {
            zomatoService.fetchRestaurantsData(
                mapCoordinates!!,
                radius ?: sharedPreferences.getDefaultRadius(),
                sharedPreferences.retrieveSelectedCuisines()
            )
        }
    }
}