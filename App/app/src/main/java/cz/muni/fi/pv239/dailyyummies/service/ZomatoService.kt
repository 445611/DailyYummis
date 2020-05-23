package cz.muni.fi.pv239.dailyyummies.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import cz.muni.fi.pv239.dailyyummies.service.networking.Retrofit
import cz.muni.fi.pv239.dailyyummies.service.networking.data.DailyMenu
import cz.muni.fi.pv239.dailyyummies.service.networking.data.DailyMenuResult
import cz.muni.fi.pv239.dailyyummies.service.networking.data.RestaurantHolder
import cz.muni.fi.pv239.dailyyummies.service.networking.data.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ZomatoService(private val context: Context, private val searchResult: MutableLiveData<SearchResult>) {

    private val retrofit = Retrofit(context)

    private lateinit var result: SearchResult

    companion object {
        const val BRNO = "Brno"
    }

    /*fun fetchData(city: String?) {
        retrofit.myApiJson.getCities(city ?: "")
            .enqueue(object : Callback<CitiesResponse> {
            override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                Log.w("Retrofit", "Enqueue cities failed!")
            }

            override fun onResponse(call: Call<CitiesResponse>, response: Response<CitiesResponse>) {
                if (!response.isSuccessful) {
                    Log.w("Retrofit", "Response cities not successful!")
                } else {
                    response.body()?.let {

                        Log.d("Retrofit size", it.cities.size.toString())
                        Log.d("Retrofit name", it.cities.get(0).name)
                    }
                }
            }
        })
    }*/

    fun fetchRestaurantsData(mapCoordinates: LatLng, radius: Int) {
        searchResult.postValue(SearchResult())
        result = SearchResult()
        fetchRestaurantApi(mapCoordinates, radius, 0)
        fetchRestaurantApi(mapCoordinates, radius, 20)
        fetchRestaurantApi(mapCoordinates, radius, 40)
        fetchRestaurantApi(mapCoordinates, radius, 60)
        fetchRestaurantApi(mapCoordinates, radius, 80)
    }

    private fun fetchRestaurantApi(
        mapCoordinates: LatLng,
        radius: Int,
        start: Int
    ) {
        retrofit.myApiJson.searchRestaurantsByMapCoordinates(
            mapCoordinates.latitude.toString(),
            mapCoordinates.longitude.toString(),
            radius,
            start
        )
            .enqueue(object : Callback<SearchResult> {
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    Log.w("Retrofit", "Enqueue restaurants by map coordinates failed!")
                }

                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    if (!response.isSuccessful) {
                        if (response.code() == 500) {
                            Toast.makeText(context, "API Failed!", Toast.LENGTH_LONG).show()
                        }
                        Log.w("Retrofit", "Response restaurants by map coordinates not successful!")
                    } else {
                        response.body()?.let { it ->
                            it.restaurants.forEach { restaurant ->
                                Log.d("Restaurant", restaurant.restaurant.name)
                            }
                            calculateRestaurantDistances(mapCoordinates, radius, it.restaurants)
                            fetchMenus(it.restaurants)
                        }
                    }
                }
            })
    }

    private fun calculateRestaurantDistances(mapCoordinates: LatLng, radius: Int, restaurants: List<RestaurantHolder>) {
        restaurants.forEach { it.restaurant.distance = SphericalUtil.computeDistanceBetween(mapCoordinates, it.restaurant.location).toInt() }
    }

    private fun fetchMenus(restaurants: List<RestaurantHolder>) {

        restaurants.forEach { restaurant ->
            retrofit.myApiJson.getRestaurantDailyMenu(restaurant.restaurant.id)
                .enqueue(object : Callback<DailyMenuResult> {
                override fun onFailure(call: Call<DailyMenuResult>, t: Throwable) {
                    Log.w("Retrofit", "Enqueue menus by map coordinates failed!")
                }

                override fun onResponse(call: Call<DailyMenuResult>, response: Response<DailyMenuResult>) {
                    if (!response.isSuccessful) {
                        Log.w("Retrofit", "Response menus by map coordinates not successful!")
                    } else {
                        response.body()?.let { it ->
                            if (it.dailyMenus.isEmpty()) {
                                return@let
                            }
                            restaurant.restaurant.menu = it.dailyMenus[0].dailyMenu
                            result.restaurants.add(restaurant)
                            result.restaurants.sortBy { it.restaurant.distance }
                            searchResult.postValue(result)
                        }
                    }
                }
            })
        }

    }



}