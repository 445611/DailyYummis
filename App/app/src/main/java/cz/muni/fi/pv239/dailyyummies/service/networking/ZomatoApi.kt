package cz.muni.fi.pv239.dailyyummies.service.networking

import cz.muni.fi.pv239.dailyyummies.service.networking.data.CitiesResult
import cz.muni.fi.pv239.dailyyummies.service.networking.data.Cuisine
import cz.muni.fi.pv239.dailyyummies.service.networking.data.DailyMenuResult
import cz.muni.fi.pv239.dailyyummies.service.networking.data.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ZomatoApi {

    companion object {
        const val ZOMATO_API_TOKEN = "13fd02ba025ac2eff001b779ba95ef02";
    }

    @Headers("user-key: $ZOMATO_API_TOKEN")
    @GET("cities")
    fun getCities(@Query("q") cityName: String): Call<CitiesResult>

    @Headers("user-key: $ZOMATO_API_TOKEN")
    @GET("cuisines")
    fun getCuisinesForCity(@Query("city_id") cityId: Int): Call<List<Cuisine>>


    /**
     * categories:
     * 7 - Daily menus
     * 9 - Lunch
     */
    @Headers("user-key: $ZOMATO_API_TOKEN")
    @GET("search")
    fun searchRestaurantsByMapCoordinates(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("radius") radius: Int, @Query("start") start: Int, @Query("sort") sortBy: String = "real_distance", @Query("category") category: String = "9"): Call<SearchResult>

    @Headers("user-key: $ZOMATO_API_TOKEN")
    @GET("dailymenu")
    fun getRestaurantDailyMenu(@Query("res_id") restaurantId: String): Call<DailyMenuResult>


    /*@Headers("user-key: $ZOMATO_API_TOKEN")
    @GET("search")
    fun searchMenusByCityId(@Query("entity_type") entityType: String = "city", @Query("entity_id") cityId: String): Call<SearchResult>*/

}