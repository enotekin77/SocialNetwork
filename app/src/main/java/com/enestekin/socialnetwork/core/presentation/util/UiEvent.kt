package com.enestekin.socialnetwork.core.presentation.util

import com.enestekin.socialnetwork.core.util.UiText

sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()

}