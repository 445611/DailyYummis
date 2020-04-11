package com.example.dailyyummies.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dailyyummies.data.SharedPreferences
import com.example.dailyyummies.databinding.FragmentHomeBinding
import com.example.dailyyummies.databinding.FragmentMapBinding
import com.example.dailyyummies.map.MapViewModel

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel

    private lateinit var binding: FragmentMapBinding

    private lateinit var myPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            myPref = SharedPreferences(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        mapViewModel =
            ViewModelProviders.of(this).get(MapViewModel::class.java)
        mapViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        return binding.root
    }

}