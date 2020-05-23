package cz.muni.fi.pv239.dailyyummies.service.networking.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

class SearchResult(@SerializedName("results_found") val resultsFound: Int = 0, val restaurants: MutableList<RestaurantHolder> = mutableListOf())

class RestaurantHolder(val restaurant: Restaurant = Restaurant(), var isExpanded: Boolean = false)

class Restaurant(val id: String = "", val name: String = "", @SerializedName("user_rating") val rating: UserRating = UserRating(), var distance: Int = 0, val location: LatLng? = null, var menu: DailyMenu = DailyMenu())

class UserRating(@SerializedName("aggregate_rating") val userRating: Double = 0.0, @SerializedName("rating_text") val userRatingText: String = "")