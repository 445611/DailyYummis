package com.example.dailyyummies.data

import android.content.Context

class SharedPreferences(context: Context) {
    companion object {
        const val MY_PREF = "my_pref_android"
    }

    val preferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
}