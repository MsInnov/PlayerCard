package com.mscode.playercard.view.models

sealed interface ScreenStateUiModel<T> {
    data class Loading<T>(val title: String) : ScreenStateUiModel<T>
    data class Error<T>(val errorInfo: String) : ScreenStateUiModel<T>
    data class Success<T>(val content: T) : ScreenStateUiModel<T>
}
