package com.example.gameb.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gameb.R
import com.example.gameb.databinding.FragmentSplashscreenBinding

class SplashFragment : Fragment(R.layout.fragment_splashscreen) {
    companion object{
        fun newInstance() = AuthFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashscreenBinding.bind(view)
    }
}
