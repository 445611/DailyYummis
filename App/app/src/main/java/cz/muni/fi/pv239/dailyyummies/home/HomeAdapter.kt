package cz.muni.fi.pv239.dailyyummies.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.service.networking.data.CuisineHolder
import cz.muni.fi.pv239.dailyyummies.utils.inflate
import kotlinx.android.synthetic.main.home_food_row.view.*

class HomeAdapter(private val selectedCuisinesIds: MutableList<Int>, private val cuisines: List<CuisineHolder>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val row = parent.inflate(R.layout.home_food_row)
        return CustomViewHolder(row)
    }

    override fun getItemCount(): Int {
        return cuisines.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cuisines[position], selectedCuisinesIds)
    }
}

class CustomViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(item: CuisineHolder, selectedCuisinesIds: MutableList<Int>) {
        view.food_type_checkbox.text = item.cuisine.name
        view.food_type_checkbox.isChecked = item.cuisine.id in selectedCuisinesIds
        view.food_type_checkbox.setOnClickListener {
            if (view.food_type_checkbox.isChecked) {
                selectedCuisinesIds.add(item.cuisine.id)
            } else {
                selectedCuisinesIds.remove(item.cuisine.id)
            }
        }
    }
}
