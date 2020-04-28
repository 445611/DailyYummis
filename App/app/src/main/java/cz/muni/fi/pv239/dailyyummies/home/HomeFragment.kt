package cz.muni.fi.pv239.dailyyummies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.fi.pv239.dailyyummies.home.FoodType
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var selectedFoodTypes: MutableSet<FoodType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initFoodTypes(view)

        // Example how to work with SharedViewModel
        /*viewModel.sharedText.observe(viewLifecycleOwner, Observer {
            view.food_list_text.text = viewModel.sharedText.value
        })


        view.button.setOnClickListener {
            viewModel.setHomeFeed("Button")
        }

        view.button2.setOnClickListener {
            viewModel.setHomeFeed("Button2")
        }*/

        return view
    }

    private fun initFoodTypes(view: View) {
        view.home_food_types.layoutManager = LinearLayoutManager(context)
        selectedFoodTypes = viewModel.sharedPreferences.retrieveSelectedFoodTypes()
        view.home_food_types.adapter = HomeAdapter(selectedFoodTypes)
    }

    override fun onPause() {
        super.onPause()
        viewModel.sharedPreferences.setSelectedFoodTypes(selectedFoodTypes)
    }
}