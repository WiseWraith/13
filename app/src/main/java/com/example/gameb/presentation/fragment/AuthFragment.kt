package com.example.gameb.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gameb.R
import com.example.gameb.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {
    companion object{
        fun newInstance() = AuthFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAuthBinding.bind(view)
    }
}

