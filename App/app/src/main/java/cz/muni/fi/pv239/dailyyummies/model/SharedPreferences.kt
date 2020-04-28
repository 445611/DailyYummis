package cz.muni.fi.pv239.dailyyummies.model

import android.content.Context

class SharedPreferences(context: Context){

    companion object {
        const val MY_PREF = "dailyyummies_preferences"
        //const val APP_OPEN_COUNT = "app_open_count"
    }
    val preferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    /*fun getAppOpenCount(): Int{
        return preferences.getInt(APP_OPEN_COUNT, 0)
    }

    fun setAppOpenCount(count: Int){
        preferences.edit().putInt(APP_OPEN_COUNT, count).apply()
    }*/
}