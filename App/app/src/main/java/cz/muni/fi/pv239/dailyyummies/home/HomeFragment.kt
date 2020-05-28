package cz.muni.fi.pv239.dailyyummies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var selectedCuisinesIds: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initCheckBoxes(view)
        initCuisines(view)
        initHomeWarning(view)
        initHomeLoading(view)

        return view
    }

    private fun initCheckBoxes(view: View) {
        view.checkBox_checkAll.setOnClickListener {
            view.checkBox_checkAll.isChecked = true
            val cuisines = viewModel
                .cuisinesSearchResult
                .value
                ?.cuisines
                ?.map { it.cuisine.id }
                ?.toMutableList() ?: mutableListOf()
            selectedCuisinesIds.removeAll(cuisines)
            selectedCuisinesIds.addAll(cuisines)
            view.home_cuisines.adapter?.notifyDataSetChanged()
        }

        view.checkBox_uncheckAll.setOnClickListener {
            view.checkBox_uncheckAll.isChecked = false
            selectedCuisinesIds.removeAll(
                viewModel
                    .cuisinesSearchResult
                    .value
                    ?.cuisines
                    ?.map { it.cuisine.id }
                    ?.toMutableList() ?: mutableListOf())
            view.home_cuisines.adapter?.notifyDataSetChanged()
        }
    }

    private fun initHomeLoading(view: View) {
       view.homeLoading.visibility =
           if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) View.GONE else View.VISIBLE
    }

    private fun initHomeWarning(view: View) {
        view.homeWarning.visibility =
            if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) View.VISIBLE else View.GONE
    }

    private fun initCuisines(view: View) {
        view.home_cuisines.layoutManager = LinearLayoutManager(context)

        selectedCuisinesIds = viewModel.sharedPreferences.retrieveSelectedCuisines()
        viewModel.cuisinesSearchResult.observe(viewLifecycleOwner, Observer {
            view.home_cuisines.adapter = HomeAdapter(selectedCuisinesIds, it.cuisines)
            view.homeLoading.visibility = if (it.cuisines.isNotEmpty()
                || viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) View.GONE else View.VISIBLE
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.sharedPreferences.setSelectedCuisines(selectedCuisinesIds, viewModel.cuisinesSearchResult.value?.cuisines ?: emptyList())
    }

    override fun onResume() {
        super.onResume()

        view?.let {
            initHomeWarning(it)
            initHomeLoading(it)
        }

        viewModel.fetchApiCuisinesData()
    }
}