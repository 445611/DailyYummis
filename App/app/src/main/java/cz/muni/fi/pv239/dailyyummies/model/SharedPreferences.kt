package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context
import android.content.SharedPreferences.Editor
import cz.muni.fi.pv239.dailyyummies.service.networking.data.Cuisine
import cz.muni.fi.pv239.dailyyummies.service.networking.data.CuisineHolder

class SharedPreferences(context: Context) {

    companion object {
        const val MY_PREF = "dailyyummies_preferences"
        const val DEFAULT_HOME = "default_home"
        const val DEFAULT_RADIUS = "default_radius"
        const val SELECTED_CUISINES = "selected_cuisines"
    }

    val preferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    fun setSelectedCuisines(selectedCuisines: MutableList<Int>, allCuisines: List<CuisineHolder>) {
        val editor: Editor = preferences.edit()
        selectedCuisines.filter { allCuisines.map { cuisineHolder ->  cuisineHolder.cuisine.id }.contains(it) }
        editor.putStringSet(SELECTED_CUISINES, selectedCuisines.map { it.toString() }.toSet())
        editor.apply()
    }

    fun retrieveSelectedCuisines(): MutableList<Int> {
        val a = preferences.getStringSet(SELECTED_CUISINES, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toMutableList() ?: mutableListOf()
        return a
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