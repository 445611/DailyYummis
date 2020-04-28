package cz.muni.fi.pv239.dailyyummies.menu

import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.Meal
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.MenuAdapter
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.Restaurant
import cz.muni.fi.pv239.dailyyummies.utils.inflate
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.menu_restaurant_row.view.*

class RestaurantAdapter(private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val row = parent.inflate(R.layout.menu_restaurant_row)
        return RestaurantViewHolder(row)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

}

class RestaurantViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Restaurant) {
        view.restaurant_name.text = item.name
        view.restaurant_rating.text = item.rating.toString()
        view.restaurant_distance.text = item.distance.toString()
        view.restaurant_menu.layoutManager = LinearLayoutManager(view.context)
        view.restaurant_menu.adapter = MenuAdapter(item.menu)
    }
}
