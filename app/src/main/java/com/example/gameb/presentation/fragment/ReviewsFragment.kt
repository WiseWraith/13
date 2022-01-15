package com.example.gameb.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameb.R
import com.example.gameb.presentation.activity.MainActivity
import com.example.gameb.presentation.adapter.GameAdapter
import com.example.gameb.presentation.adapter.ReviewAdapter
import com.example.gameb.data.network.NetworkService
import com.example.gameb.databinding.FragmentReviewsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReviewsFragment : Fragment(R.layout.fragment_reviews) {
    private lateinit var binding: FragmentReviewsBinding
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ context,exception ->
        binding.progressBar.visibility = View.GONE
        binding.rvReview.adapter =
            GameAdapter(listOf()) {}
        binding.swRefreshRW.isRefreshing = false
        Snackbar.make(
            requireView(),
            getString(R.string.error),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.parseColor("#ED4337"))
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .show()
    }

    companion object{
        fun newInstance() = ReviewsFragment()
    }

    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewsBinding.bind(view)

        binding.imageClose.setOnClickListener {
            (activity as MainActivity).navigateToFragment(
                ProductFragment.NewInstance()
            )
        }
        loadReview()

        binding.swRefreshRW.setOnRefreshListener {
            binding.swRefreshRW.isRefreshing = true
            loadReview()
            binding.swRefreshRW.isRefreshing = false
        }
    }
    @ExperimentalSerializationApi
    private fun loadReview() {
        scope.launch {
            val reviews = NetworkService.loadReviews()
            binding.rvReview.layoutManager = LinearLayoutManager(context)
            binding.rvReview.adapter = ReviewAdapter(reviews) {}
            binding.progressBar.visibility = View.GONE
            binding.swRefreshRW.isRefreshing = false
        }
    }
}