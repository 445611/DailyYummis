package cz.muni.fi.pv239.dailyyummies.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import cz.muni.fi.pv239.dailyyummies.service.networking.Retrofit
import cz.muni.fi.pv239.dailyyummies.service.networking.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ZomatoService(private val context: Context, private val restaurantsSearchResult: MutableLiveData<RestaurantSearchResult>, private val cuisinesSearchResult: MutableLiveData<CuisineResult>) {

    private val retrofit = Retrofit(context)

    private lateinit var restaurantResult: RestaurantSearchResult

    companion object {
        const val BRNO = "Brno"
    }

    fun fetchCuisinesForCityData(city: String?) {
        fetchCityData(city)
    }

    private fun fetchCityData(city: String?) {
        retrofit.myApiJson.getCities(city ?: "")
            .enqueue(object : Callback<CitiesResult> {
            override fun onFailure(call: Call<CitiesResult>, t: Throwable) {
                Log.w("Retrofit", "Enqueue cities failed!")
            }

            override fun onResponse(call: Call<CitiesResult>, response: Response<CitiesResult>) {
                if (!response.isSuccessful) {
                    /*if (response.code() == 500) {
                        Toast.makeText(context, "API Failed!", Toast.LENGTH_SHORT).show()
                    }*/
                    Log.w("Retrofit", "Response cities not successful!")
                } else {
                    response.body()?.let {
                        if (it.cities.isNotEmpty()) {
                            fetchCuisinesData(it.cities[0].id)
                        }
                    }
                }
            }
        })
    }

    private fun fetchCuisinesData(cityId: Int) {
        retrofit.myApiJson.getCuisinesForCity(cityId)
            .enqueue(object: Callback<CuisineResult> {
                override fun onFailure(call: Call<CuisineResult>, t: Throwable) {
                    Log.w("Retrofit", "Enqueue cuisines failed!")
                }

                override fun onResponse(
                    call: Call<CuisineResult>,
                    response: Response<CuisineResult>
                ) {
                    if (!response.isSuccessful) {
                        /*if (response.code() == 500) {
                            Toast.makeText(context, "API Failed!", Toast.LENGTH_SHORT).show()
                        }*/
                        Log.w("Retrofit", "Response cuisines not successful!")
                    } else {
                        response.body()?.let {
                            if (it.cuisines.isNotEmpty()) {
                                cuisinesSearchResult.postValue(it)
                            }
                        }
                    }
                }

            })
    }

    fun fetchRestaurantsData(mapCoordinates: LatLng, radius: Int, cuisinesIds: List<Int>) {
        restaurantsSearchResult.postValue(RestaurantSearchResult())
        restaurantResult = RestaurantSearchResult()
        //MOCKED
        restaurantResult.restaurants.add(RestaurantHolder(Restaurant(name = "Fiktivna restika", location = LatLng(49.202429 , 16.6010761), distance = 100)))
        restaurantResult.restaurants.add(RestaurantHolder(Restaurant(name = "Fiktivna restika1", location = LatLng(49.202039, 16.6011060), distance = 200)))
        restaurantResult.restaurants.add(RestaurantHolder(Restaurant(name = "Fiktivna restika2", location = LatLng(49.202549, 16.6010462), distance = 300)))
        restaurantResult.restaurants.sortBy { it.restaurant.distance }
        restaurantsSearchResult.postValue(restaurantResult)
        //MOCKED

//        fetchRestaurantApi(mapCoordinates, radius, 0, cuisinesIds.joinToString(separator = ","))
//        fetchRestaurantApi(mapCoordinates, radius, 20, cuisinesIds.joinToString(separator = ","))
//        fetchRestaurantApi(mapCoordinates, radius, 40, cuisinesIds.joinToString(separator = ","))
//        fetchRestaurantApi(mapCoordinates, radius, 60, cuisinesIds.joinToString(separator = ","))
//        fetchRestaurantApi(mapCoordinates, radius, 80, cuisinesIds.joinToString(separator = ","))
    }

    private fun fetchRestaurantApi(
        mapCoordinates: LatLng,
        radius: Int,
        start: Int,
        cuisinesIdsSeparatedByComma: String
    ) {
        retrofit.myApiJson.searchRestaurantsByMapCoordinates(
            mapCoordinates.latitude.toString(),
            mapCoordinates.longitude.toString(),
            radius,
            start,
            cuisinesIdsSeparatedByComma
        )
            .enqueue(object : Callback<RestaurantSearchResult> {
                override fun onFailure(call: Call<RestaurantSearchResult>, t: Throwable) {
                    Log.w("Retrofit", "Enqueue restaurants by map coordinates failed!")
                }

                override fun onResponse(
                    call: Call<RestaurantSearchResult>,
                    response: Response<RestaurantSearchResult>
                ) {
                    if (!response.isSuccessful) {
                        /*if (response.code() == 500) {
                            Toast.makeText(context, "API Failed!", Toast.LENGTH_SHORT).show()
                        }*/
                        Log.w("Retrofit", "Response restaurants by map coordinates not successful!")
                    } else {
                        response.body()?.let { it ->
                            it.restaurants.forEach { restaurant ->
                                Log.d("Restaurant", restaurant.restaurant.name)
                            }
                            calculateRestaurantDistances(mapCoordinates, radius, it.restaurants)
                            it.restaurants.removeAll { it.restaurant.distance > radius }
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
                            restaurantResult.restaurants.add(restaurant)
                            restaurantResult.restaurants.sortBy { it.restaurant.distance }
                            restaurantsSearchResult.postValue(restaurantResult)
                        }
                    }
                }
            })
        }

    }



}