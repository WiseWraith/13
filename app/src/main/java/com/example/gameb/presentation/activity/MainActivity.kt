package com.example.gameb.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.gameb.R
import com.example.gameb.databinding.ActivityMainBinding
import com.example.gameb.presentation.fragment.GamesFragment
import com.example.gameb.presentation.fragment.ShopsFragment


class MainActivity : FragmentActivity() {
    fun navigateToFragment(fmt: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fmt)
            .addToBackStack(fmt.javaClass.name)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToFragment(GamesFragment.NewInstance())
        }
    }
}