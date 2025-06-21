package io.github.rubenquadros.timetowish.feature.generatewish.presentation

sealed interface GenerateWishUiEvent {
    data class CopyToClipBoard(val data: String) : GenerateWishUiEvent
}