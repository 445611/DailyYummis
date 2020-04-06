package com.example.dailyyummies.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyyummies.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.include))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}