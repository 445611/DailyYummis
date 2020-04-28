package cz.muni.fi.pv239.dailyyummies.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.utils.inflate
import kotlinx.android.synthetic.main.home_food_row.view.*

class HomeAdapter(private val selectedFoodTypes: MutableSet<FoodType>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val row = parent.inflate(R.layout.home_food_row)
        return CustomViewHolder(row)
    }

    override fun getItemCount(): Int {
        return FoodType.allFoodTypes().size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(FoodType.allFoodTypes()[position], selectedFoodTypes)
    }
}

class CustomViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(item: FoodType, selectedFoodTypes: MutableSet<FoodType>) {

        view.food_type_checkbox.text = item.foodName
        if (item in selectedFoodTypes) {
            view.food_type_checkbox.isChecked = true
        }

        view.food_type_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedFoodTypes.add(item)
            } else {
                selectedFoodTypes.remove(item)
            }
        }
    }
}
