package cz.muni.fi.pv239.dailyyummies.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.muni.fi.pv239.dailyyummies.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.include))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}