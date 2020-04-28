package cz.muni.fi.pv239.dailyyummies.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.menu.restaurant.Restaurant
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var restaurants: List<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        initRestaurants(view)

        return view;
    }

    private fun initRestaurants(view: View) {
        view.menu_restaurants.layoutManager = LinearLayoutManager(context)
        restaurants = viewModel.getAllRestaurants();
        view.menu_restaurants.adapter = RestaurantAdapter(restaurants)
    }
}