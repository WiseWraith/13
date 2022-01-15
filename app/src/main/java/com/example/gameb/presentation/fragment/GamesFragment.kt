package com.example.gameb.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameb.R
import com.example.gameb.presentation.ScreenState
import com.example.gameb.presentation.activity.MainActivity
import com.example.gameb.presentation.adapter.GameAdapter
import com.example.gameb.data.network.NetworkService
import com.example.gameb.databinding.FragmentGamesBinding
import com.example.gameb.domain.model.Game
import com.example.gameb.onClickFlow
import com.example.gameb.onRefreshFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi as ExperimentalSerializationApi

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class GamesFragment : Fragment(R.layout.fragment_games)  {
    private lateinit var binding: FragmentGamesBinding
    private val GameViewModel by lazy { GameViewModel(requireContext(), lifecycleScope)}

    companion object{
        fun NewInstance() = GamesFragment()
    }

    private fun setLoading(isLoading: Boolean) = with(binding) {
        progressBar.isVisible = isLoading && !rvGames.isVisible
        swRefreshGM.isRefreshing = isLoading && rvGames.isVisible
    }
    private fun setData(games: List<Game>?) = with(binding){
        rvGames.isVisible = games != null
        binding.rvGames.layoutManager = LinearLayoutManager(context)
        rvGames.adapter =
            GameAdapter(games ?: listOf()){
                (activity as MainActivity).navigateToFragment(
                    ProductFragment.NewInstance()
                )
            }
        binding.imageProfile.setOnClickListener {
            (activity as MainActivity).navigateToFragment(
                ProfileFragment.newInstance()
            )
        }
    }
    private fun setError(message: String?) = with(binding){
        ErrLayout.isVisible = message != null
        textError.text = message
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)
        merge(
            flowOf(Unit),
            binding.swRefreshGM.onRefreshFlow(),
            binding.buttonError.onClickFlow()
        )
            .flatMapLatest{loadGames()}
            .distinctUntilChanged()
            GameViewModel.screenState.onEach{
                when(it){
                    is ScreenState.DataLoaded -> {
                        setLoading(false)
                        setError(null)
                        setData(it.game)
                    }
                    is ScreenState.Error -> {
                        setLoading(false)
                        setError(it.error)
                        setData(null)
                    }
                    ScreenState.Loading -> {
                        setLoading(true)
                        setError(null)
                    }
                }
            }
            .launchIn(lifecycleScope)

        if(savedInstanceState == null) {
            GameViewModel.loadData()
        }
        binding.swRefreshGM.setOnRefreshListener {
            GameViewModel.loadData()
        }
        binding.swRefreshGM.setOnRefreshListener {
            GameViewModel.loadData()
        }
    }
    @ExperimentalSerializationApi
    private fun loadGames() = flow {

        emit(ScreenState.Loading)
        val games = NetworkService.loadGames()
        emit(ScreenState.DataLoaded(games))
    }
        .catch{
            emit(ScreenState.Error(getString(R.string.error_connect)))
        }
}



