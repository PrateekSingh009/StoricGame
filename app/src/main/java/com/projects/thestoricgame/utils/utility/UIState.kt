package com.projects.thestoricgame.utils.utility

sealed class UIState<out T> {
    data object Loading: UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Failure(val error : String?) : UIState<Nothing>()
}