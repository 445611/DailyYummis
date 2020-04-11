package com.example.dailyyummies.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dailyyummies.data.SharedPreferences
import com.example.dailyyummies.databinding.FragmentMealsBinding

class MealFragment : Fragment() {

    private lateinit var mealViewModel: MealViewModel

    private lateinit var binding: FragmentMealsBinding

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

        binding = FragmentMealsBinding.inflate(layoutInflater, container, false)
        mealViewModel =
            ViewModelProviders.of(this).get(MealViewModel::class.java)
        mealViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        return binding.root
    }

}