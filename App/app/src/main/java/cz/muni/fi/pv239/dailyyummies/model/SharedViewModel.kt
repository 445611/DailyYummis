package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.Meal
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.Restaurant

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

    fun getMeals(): MutableSet<Meal> {
        return mutableSetOf(
            Meal("Rizek a pivo", 125.toFloat()),
            Meal("Knedlo vepro zeli", 119.toFloat()),
            Meal("Rizek a pivo", 125.toFloat()),
            Meal("Knedlo vepro zeli", 119.toFloat()),
            Meal("Rizek a pivo", 125.toFloat()),
            Meal("Knedlo vepro zeli", 119.toFloat()),
            Meal("Rizek a pivo", 125.toFloat()),
            Meal("Knedlo vepro zeli", 119.toFloat())
        )
    }

    fun getAllRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                "U karla",
                5.toFloat(),
                600,
                getMeals()
            ),
            Restaurant(
                "U Drevaka",
                4.5.toFloat(),
                200,
                getMeals()
            ),
            Restaurant(
                "U karla",
                5.toFloat(),
                600,
                getMeals()
            ),
            Restaurant(
                "U Drevaka",
                4.5.toFloat(),
                200,
                getMeals()
            ),
            Restaurant(
                "U karla",
                5.toFloat(),
                600,
                getMeals()
            ),
            Restaurant(
                "U Drevaka",
                4.5.toFloat(),
                200,
                getMeals()
            )
        )
    }
}