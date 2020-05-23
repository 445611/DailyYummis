package cz.muni.fi.pv239.dailyyummies.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.progressBar.visibility = View.VISIBLE
        initRestaurants(view)

        return view;
    }

    private fun initRestaurants(view: View) {
        view.menu_restaurants.layoutManager = LinearLayoutManager(context)
        viewModel.restaurantsSearchResult.observe(viewLifecycleOwner, Observer {
            if (viewModel.restaurantsSearchResult.value!!.restaurants.isEmpty()) {
                view.progressBar.visibility = View.VISIBLE
            } else {
                view.progressBar.visibility = View.GONE
            }
            view.menu_restaurants.adapter = RestaurantAdapter(viewModel.restaurantsSearchResult.value!!.restaurants, context)
        })
    }

    override fun onResume() {
        super.onResume()

        // Coordinates from GOOGLE MAPS set like this 48.725166, 21.276871
        // 49.194935, 16.608381
        //KO 49.210928, 16.593532
        // 49.193176 16.610455 OREL
        viewModel.mapCoordinates = LatLng(49.193176, 16.610455)
        viewModel.fetchApiRestaurantsData()
    }

    override fun onStart() {
        super.onStart()
    }
}