package cz.muni.fi.pv239.dailyyummies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

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
}