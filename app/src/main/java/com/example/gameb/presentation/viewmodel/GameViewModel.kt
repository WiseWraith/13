package com.example.gameb.presentation.viewmodel

import android.content.Context
import com.example.gameb.data.network.NetworkService
import com.example.gameb.presentation.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel (
    private val context: Context,
    private val coroutineScope: CoroutineScope
) {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState
    private var job: Job? = null

    fun loadData() {
        job?.cancel()
        job = coroutineScope.launch {
            try {
                _screenState.emit(ScreenState.Loading)
                val games = NetworkService.loadGames()
                _screenState.emit(ScreenState.DataLoaded(games))
            } catch (ex: Throwable) {
                _screenState.emit(ScreenState.Error("Ошибка!"))
            }
        }
    }
}