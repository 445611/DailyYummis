package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import android.content.SharedPreferences.Editor
import cz.muni.fi.pv239.dailyyummies.home.FoodType


class SharedPreferences(context: Context) {

    companion object {
        const val MY_PREF = "dailyyummies_preferences"
        const val DEFAULT_HOME = "default_home"
        const val DEFAULT_RADIUS = "default_radius"
        const val FOOD_TYPES = "food_types"
    }

    val preferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    fun setSelectedFoodTypes(foodTypes: Set<FoodType>) {
        val editor: Editor = preferences.edit()
        editor.putStringSet(FOOD_TYPES, foodTypes.map { it.name }.toSet())
        editor.apply()
    }

    fun retrieveSelectedFoodTypes(): MutableSet<FoodType> {
        val set = preferences.getStringSet(FOOD_TYPES, null)?.map { FoodType.valueOf(it) }
        return set?.toMutableSet() ?: mutableSetOf()
    }

    fun getDefaultHome(): String? {
        return preferences.getString(DEFAULT_HOME, "");
    }

    fun setDefaultHome(new_home: String) {
        preferences.edit().putString(DEFAULT_HOME, new_home).apply()
    }

    fun getDefaultRadius(): Int {
        return preferences.getInt(DEFAULT_RADIUS, 500);
    }

    fun setDefaultRadius(new_radius: Int) {
        preferences.edit().putInt(DEFAULT_RADIUS, new_radius).apply()
    }
}