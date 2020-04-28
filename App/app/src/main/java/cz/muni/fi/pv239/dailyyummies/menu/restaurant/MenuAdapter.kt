package cz.muni.fi.pv239.dailyyummies.menu.restaurant

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.utils.inflate
import kotlinx.android.synthetic.main.menu_restaurant_menu_row.view.*

class MenuAdapter(private val meals: MutableSet<Meal>) :
    RecyclerView.Adapter<MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val row = parent.inflate(R.layout.menu_restaurant_menu_row)
        return MealViewHolder(row)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals.toList()[position])
    }

}

class MealViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(meal: Meal) {
        view.meal_name.text = meal.name
        view.meal_price.text = meal.price.toString()
    }
}