package com.example.gameb.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gameb.R
import com.example.gameb.presentation.activity.MainActivity
import com.example.gameb.databinding.FragmentGameProductBinding

class ProductFragment : Fragment(R.layout.fragment_game_product){

        companion object {
            fun NewInstance() = ProductFragment() }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val binding = FragmentGameProductBinding.bind(view)
            binding.imageProfile.setOnClickListener{
                (activity as MainActivity).navigateToFragment(ProfileFragment.newInstance())
            }
            binding.imageClose.setOnClickListener{
                (activity as MainActivity).navigateToFragment(GamesFragment.NewInstance())
            }
            binding.reviews.setOnClickListener{
                (activity as MainActivity).navigateToFragment(ReviewsFragment.newInstance())
            }
            binding.shops.setOnClickListener{
                (activity as MainActivity).navigateToFragment(ShopsFragment.NewInstance())
            }

        }
}
