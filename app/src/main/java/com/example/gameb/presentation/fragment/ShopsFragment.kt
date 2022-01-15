package com.example.gameb.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameb.R
import com.example.gameb.presentation.activity.MainActivity
import com.example.gameb.presentation.adapter.ShopAdapter
import com.example.gameb.data.network.NetworkService
import com.example.gameb.databinding.FragmentShopsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ShopsFragment : Fragment(R.layout.fragment_shops) {
    private lateinit var binding: FragmentShopsBinding
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ context,exception ->
        binding.progressBar.visibility = View.GONE
        binding.rvShops.adapter =
            ShopAdapter(listOf()) {}
        binding.swRefreshSH.isRefreshing = false
        Snackbar.make(
            requireView(),
            getString(R.string.error),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.parseColor("#ED4337"))
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .show()
    }

    companion object {
        fun NewInstance() = ShopsFragment()
    }

    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShopsBinding.bind(view)

        binding.imageClose.setOnClickListener {
            (activity as MainActivity).navigateToFragment(
                ProductFragment.NewInstance()
            )
        }
        loadShops()

        binding.swRefreshSH.setOnRefreshListener {
            binding.swRefreshSH.isRefreshing = true
            loadShops()
            binding.swRefreshSH.isRefreshing = false
        }
    }
    @ExperimentalSerializationApi
    private fun loadShops() {
        scope.launch {
            val shops = NetworkService.loadShops()
            binding.rvShops.layoutManager = LinearLayoutManager(context)
            binding.rvShops.adapter = ShopAdapter(shops) {}
            binding.progressBar.visibility = View.GONE
            binding.swRefreshSH.isRefreshing = false
        }
    }
}
