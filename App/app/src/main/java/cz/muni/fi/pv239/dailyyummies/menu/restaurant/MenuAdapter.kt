package cz.muni.fi.pv239.dailyyummies.menu.restaurant

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.service.networking.data.Dish
import cz.muni.fi.pv239.dailyyummies.service.networking.data.DishHolder
import cz.muni.fi.pv239.dailyyummies.utils.inflate
import kotlinx.android.synthetic.main.menu_restaurant_menu_row.view.*

class MenuAdapter(private val dishes: List<DishHolder>) :
    RecyclerView.Adapter<MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val row = parent.inflate(R.layout.menu_restaurant_menu_row)
        return MealViewHolder(row)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(dishes[position].dish)
    }

}

class MealViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(dish: Dish) {
        view.meal_name.text = dish.name
        view.meal_price.text = dish.price
    }
}