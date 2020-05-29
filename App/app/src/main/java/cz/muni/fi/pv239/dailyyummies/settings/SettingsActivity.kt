package cz.muni.fi.pv239.dailyyummies.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import cz.muni.fi.pv239.dailyyummies.MainActivity
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedPreferences

class SettingsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.include))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initSharedPreferences()
        initSettings()
        navigateToHome()
    }

    private fun navigateToHome() {
        val homeButton = findViewById<Button>(R.id.homeButton)

        homeButton.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    private fun initSharedPreferences() {
        sharedPreferences =
            SharedPreferences(this)
    }

    private fun initSettings() {
        val defaultHome: EditText = findViewById(R.id.defaultHome)
        defaultHome.addTextChangedListener {
            sharedPreferences.setDefaultHome(defaultHome.text.toString())
        }
        defaultHome.setText(sharedPreferences.getDefaultHome())

        val defaultRadius: EditText = findViewById(R.id.defaultRadius)
        defaultRadius.addTextChangedListener {
            sharedPreferences.setDefaultRadius(defaultRadius.text.toString().toIntOrNull() ?: 0)
        }
        defaultRadius.setText(sharedPreferences.getDefaultRadius().toString())
    }
}