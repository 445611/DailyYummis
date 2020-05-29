package cz.muni.fi.pv239.dailyyummies.menu

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel

class RestaurantInfoActivity : AppCompatActivity() {

    companion object {
        const val RESTAURANT_TITLE_KEY = "RestaurantTitleKey"
        const val RESTAURANT_URL_KEY = "RestaurantUrlKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_info_activity)

        title = intent.getStringExtra(RESTAURANT_TITLE_KEY)

        val webView = findViewById<WebView>(R.id.webView)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.loadUrl(intent.getStringExtra(RESTAURANT_URL_KEY))

        setSupportActionBar(findViewById(R.id.include))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}