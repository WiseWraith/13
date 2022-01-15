package com.example.gameb.presentation

import com.example.gameb.domain.model.Game


sealed class ScreenState {
    data class DataLoaded(val game: List<Game> ) : ScreenState()
    object Loading : ScreenState()
    data class Error(val error: String) : ScreenState()
}
